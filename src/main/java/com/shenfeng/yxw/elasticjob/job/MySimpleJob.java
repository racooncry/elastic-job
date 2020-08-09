package com.shenfeng.yxw.elasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.shenfeng.autoconfig.ElasticSimpleJob;
import com.shenfeng.yxw.elasticjob.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Slf4j
//@ElasticSimpleJob(jobName = "mySimpleJob",
//        cron = "0/5 * * * * ?",
//        shardingTotalCount = 1,
//        overwrite = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MySimpleJob implements SimpleJob {
    private final OrderService orderService;

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("生成数据：");
        for (int i = 0; i < 10; i++) {
            orderService.create();
        }
    }
}
