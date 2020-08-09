package com.shenfeng.autoconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "elasticjob.zookeeper")
@Setter
@Getter
@Component
public class ZookeeperProperties {
    private String serverList;
    private String namespace;

}
