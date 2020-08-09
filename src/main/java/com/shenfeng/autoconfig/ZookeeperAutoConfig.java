package com.shenfeng.autoconfig;

import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty("elasticjob.zookeeper.server-list")
@EnableConfigurationProperties(ZookeeperProperties.class)
public class ZookeeperAutoConfig {
    @Autowired
    private ZookeeperProperties zookeeperProperties;

    @Bean(initMethod = "init")
    public CoordinatorRegistryCenter zkCenter() {

        var zookeeperConfiguration = new ZookeeperConfiguration(zookeeperProperties.getServerList(),
                zookeeperProperties.getNamespace());
        var registryCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);

        return registryCenter;
    }

}
