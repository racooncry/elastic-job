package com.shenfeng.yxw.elasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.shenfeng.autoconfig.ElasticDataflowJob;
import com.shenfeng.yxw.elasticjob.dao.jdOrder.JdOrderMapper;
import com.shenfeng.yxw.elasticjob.dao.tmallOrder.TmallOrderMapper;
import com.shenfeng.yxw.elasticjob.domain.entity.allOrder.AllOrder;
import com.shenfeng.yxw.elasticjob.domain.entity.jdOrder.JdOrder;
import com.shenfeng.yxw.elasticjob.domain.entity.tmallOrder.TmallOrder;
import com.shenfeng.yxw.elasticjob.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ElasticDataflowJob(jobName = "fetchThirdJob",
        cron = "0/15 * * * * ?",
        shardingTotalCount = 2,
        overwrite = true,
        streamProcess = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FetchThirdOrderJob implements DataflowJob<Object> {
    private final OrderService orderService;

    @Override
    public List<Object> fetchData(ShardingContext shardingContext) {
//        log.info("fetch current sharding:{}, all sharding:{}", shardingContext.getShardingItem(), shardingContext.getShardingTotalCount());

        if (shardingContext.getShardingItem() == 0) {
            // 京东订单
            List<JdOrder> list = orderService.getJdNotFetchedOrder(5);
            log.info("抓取京东订单");
            if (!CollectionUtils.isEmpty(list)) {
                log.info("京东不为空");
                List<Object> collect = list.stream().map(jdOrder -> (Object) jdOrder).collect(Collectors.toList());
                return collect;
            }
        } else {
            // 天猫订单
            List<TmallOrder> list = orderService.getTmallNotFetchedOrder(5);
            log.info("抓取天猫订单");
            if (!CollectionUtils.isEmpty(list)) {
                log.info("天猫不为空");
                List<Object> collect = list.stream().map(tmallOrder -> (Object) tmallOrder).collect(Collectors.toList());
                return collect;
            }
        }

        log.info("本次任务结束");
        return null;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<Object> list) {
        Date date = new Date();
//        log.info("handle current sharding:{}, all sharding:{}", shardingContext.getShardingItem(), shardingContext.getShardingTotalCount());
        if (shardingContext.getShardingItem() == 0) {
            // 京东订单
            if (!CollectionUtils.isEmpty(list)) {
                List<JdOrder> collect = list.stream().map(d -> (JdOrder) d).collect(Collectors.toList());
                for (JdOrder jdOrder : collect) {
                    AllOrder allOrder = AllOrder.builder()
                            .createTime(date)
                            .createUser("system")
                            .thirdOrderId(jdOrder.getId())
                            .totalAmount(jdOrder.getAmount())
                            // 京东订单
                            .type(0)
                            .updateTime(date)
                            .updateUser("system")
                            .build();
                    orderService.processJdOrder(allOrder);
                }
            }
        } else {
            // 天猫订单
            if (!CollectionUtils.isEmpty(list)) {
                List<TmallOrder> collect = list.stream().map(d -> (TmallOrder) d).collect(Collectors.toList());
                for (TmallOrder tmallOrder : collect) {
                    AllOrder allOrder = AllOrder.builder()
                            .createTime(date)
                            .createUser("system")
                            .thirdOrderId(tmallOrder.getId())
                            .totalAmount(tmallOrder.getMoney())
                            // 天猫订单
                            .type(1)
                            .updateTime(date)
                            .updateUser("system")
                            .build();
                    orderService.processTmallOrder(allOrder);
                }
            }
        }
    }
}
