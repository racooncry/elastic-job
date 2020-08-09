package com.shenfeng.yxw.elasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.shenfeng.autoconfig.ElasticSimpleJob;
import com.shenfeng.yxw.elasticjob.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@ElasticSimpleJob(jobName = "thirdProduceJob",
        cron = "0/5 * * * * ?",
        shardingTotalCount = 1,
        overwrite = true)
public class ThirdOrderProduceJob implements SimpleJob {
    @Autowired
    private OrderService orderService;

    @Override
    public void execute(ShardingContext shardingContext) {

        orderService.produceThirdOrder();

    }
}
