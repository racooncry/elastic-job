package com.shenfeng.yxw.elasticjob;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@Slf4j
@MapperScan(basePackages = "com.shenfeng.yxw.elasticjob.dao")
@EnableTransactionManagement
public class ElasticJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticJobApplication.class, args);
    }

}
