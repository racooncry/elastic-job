package com.shenfeng.yxw.elasticjob.job;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

public class RunMysimpleJob {

    public static final String ZOOKEEPER_BROKER = "192.168.37.145:2181,192.168.37.145:2182,192.168.37.145:2183";
    public static final String ZOOKEEPER_NAMESPACE = "java-simple-job";

    public static void main(String[] args) {
        new JobScheduler(zkCenter(), configuration()).init();
    }

    /*
    注册中心
     */
    public static CoordinatorRegistryCenter zkCenter() {

        var zookeeperConfiguration = new ZookeeperConfiguration(ZOOKEEPER_BROKER,
                ZOOKEEPER_NAMESPACE);
        var registryCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        registryCenter.init();
        return registryCenter;
    }


    /*
    job 配置
     */
    public static LiteJobConfiguration configuration() {
        // job核心配置
        JobCoreConfiguration job = JobCoreConfiguration.newBuilder(
                "mySimpleJob",
                // 从第0秒开始，每10秒执行一次
                "0/10 * * * * ?",
                2
        ).build();

        // job类型配置
        SimpleJobConfiguration simpleJobConfiguration =
                new SimpleJobConfiguration(job, MySimpleJob.class.getCanonicalName());
        // job根的配置 （LiteJobConfiguration）
        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration
                .newBuilder(simpleJobConfiguration)
                .overwrite(true)
                .build();
        return liteJobConfiguration;
    }
}
