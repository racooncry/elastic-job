package com.shenfeng.yxw.elasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.shenfeng.autoconfig.ElasticSimpleJob;
import com.shenfeng.yxw.elasticjob.domain.entity.order.TOrder;
import com.shenfeng.yxw.elasticjob.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
//@ElasticSimpleJob(jobName = "orderCancelJob",
//        cron = "0/15 * * * * ?",
//        shardingTotalCount = 2,
//        overwrite = true)
public class OrderCancelJob implements SimpleJob {
    private final OrderService orderService;

    @Override
    public void execute(ShardingContext shardingContext) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, -30);
        // 订单尾号 % 分配总数 ==当前分配项
        List<TOrder> list = orderService.getOrder(instance,
                shardingContext.getShardingTotalCount(),
                shardingContext.getShardingItem());

        if (!CollectionUtils.isEmpty(list)) {
            ExecutorService executorService = Executors.newFixedThreadPool(4);
            for (TOrder order : list) {
                executorService.execute(() -> {
                    // 更新条件
                    Integer id = order.getId();
                    Date updateTime = order.getUpdateTime();
                    // 已取消
                    int status = 3;
                    String updateUser = "system";
                    Date updateNow = new Date();
                    orderService.cancelOrder(id, updateTime, status, updateUser, updateNow);

                });
            }
            executorService.shutdown();
        }

        List<Integer> integers = list.stream().map(TOrder::getId).collect(Collectors.toList());
        log.info("ids:{}", integers);


    }
}
