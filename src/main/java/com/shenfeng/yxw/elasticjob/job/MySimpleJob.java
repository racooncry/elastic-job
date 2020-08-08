package com.shenfeng.yxw.elasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;

@Slf4j
public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        LocalTime localTime = LocalTime.now();
        log.info("{},我是分片项：{}", localTime, shardingContext.getShardingItem() + ",总分配项：" + shardingContext.getShardingTotalCount());

    }
}
