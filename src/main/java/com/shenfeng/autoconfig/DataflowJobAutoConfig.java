package com.shenfeng.autoconfig;

import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.shenfeng.yxw.elasticjob.job.MyDataflowJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

@Configuration
@ConditionalOnBean(CoordinatorRegistryCenter.class)
@AutoConfigureAfter(ZookeeperAutoConfig.class)
public class DataflowJobAutoConfig {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CoordinatorRegistryCenter zkCenter;

    @PostConstruct
    public void initDataflowJob() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(ElasticDataflowJob.class);
        for (Map.Entry<String, Object> entry : beans.entrySet()
        ) {
            Object instance = entry.getValue();
            Class<?>[] interfaces = instance.getClass().getInterfaces();
            for (Class<?> superInterface : interfaces) {
                if (superInterface == DataflowJob.class) {
                    ElasticDataflowJob annotation = instance.getClass().getAnnotation(ElasticDataflowJob.class);
                    String jobName = annotation.jobName();
                    String cron = annotation.cron();
                    int shardingTotalCount = annotation.shardingTotalCount();
                    boolean overwrite = annotation.overwrite();
                    boolean streamProcess = annotation.streamProcess();
                    // job核心配置
                    JobCoreConfiguration job = JobCoreConfiguration.newBuilder(
                            jobName,
                            // 从第0秒开始，每10秒执行一次
                            cron,
                            shardingTotalCount
                    ).build();

                    // job类型配置
                    var dataflowJob =
                            new DataflowJobConfiguration(
                                    job,
                                    instance.getClass().getCanonicalName(),
                                    streamProcess);
                    // job根的配置 （LiteJobConfiguration）
                    LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration
                            .newBuilder(dataflowJob)
                            .overwrite(overwrite)
                            .build();
                    new JobScheduler(zkCenter, liteJobConfiguration).init();
                }
            }
        }
    }
}
