package com.shenfeng.yxw.elasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.shenfeng.autoconfig.ElasticSimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Slf4j
@ElasticSimpleJob(jobName = "mySimpleJob",
        cron = "0/10 * * * * ?",
        shardingTotalCount = 2,
        overwrite = true)
public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        LocalTime localTime = LocalTime.now();
        log.info("{},我是分片项：{}", localTime, shardingContext.getShardingItem() + ",总分配项：" + shardingContext.getShardingTotalCount());

    }
}
