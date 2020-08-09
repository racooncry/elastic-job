package com.shenfeng.yxw.elasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.shenfeng.autoconfig.ElasticDataflowJob;
import com.shenfeng.autoconfig.ElasticSimpleJob;
import com.shenfeng.yxw.elasticjob.modal.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ElasticDataflowJob(jobName = "myDataflowJob",
        cron = "0/10 * * * * ?",
        shardingTotalCount = 2,
        overwrite = true,
        streamProcess = true)
public class MyDataflowJob implements DataflowJob<Order> {

    private List<Order> orders = new ArrayList<>();

    {
        for (int i = 0; i < 100; i++) {
            Order order = Order.builder()
                    .orderId(i + 1)
                    .status(0)
                    .build();
            orders.add(order);
        }
    }

    @Override
    public List<Order> fetchData(ShardingContext shardingContext) {
        // 订单号 % 分片总数 == 当前分片数
        List<Order> collect = orders.stream().filter(o -> o.getStatus() == 0)
                .filter(o -> o.getOrderId() % shardingContext.getShardingTotalCount() == shardingContext.getShardingItem())
                .collect(Collectors.toList());
        List<Order> subList = null;
        if (!CollectionUtils.isEmpty(collect)) {
            subList = collect.subList(0, 10);
        }

        LocalTime now = LocalTime.now();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("{},我是分片项：{}，我抓取的数据是:{}", now, shardingContext.getShardingItem(), subList);
        return subList;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<Order> list) {
        list.forEach(order -> order.setStatus(1));

        LocalTime now = LocalTime.now();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("{},我是分片项：{}，我正在处理数据", now, shardingContext.getShardingItem());
    }
}
