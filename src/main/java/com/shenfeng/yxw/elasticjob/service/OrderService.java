package com.shenfeng.yxw.elasticjob.service;

import com.shenfeng.yxw.elasticjob.dao.allOrder.AllOrderMapper;
import com.shenfeng.yxw.elasticjob.dao.jdOrder.JdOrderMapper;
import com.shenfeng.yxw.elasticjob.dao.order.TOrderMapper;
import com.shenfeng.yxw.elasticjob.dao.tmallOrder.TmallOrderMapper;
import com.shenfeng.yxw.elasticjob.domain.entity.allOrder.AllOrder;
import com.shenfeng.yxw.elasticjob.domain.entity.jdOrder.JdOrder;
import com.shenfeng.yxw.elasticjob.domain.entity.order.TOrder;
import com.shenfeng.yxw.elasticjob.domain.entity.tmallOrder.TmallOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService {

    private final TOrderMapper orderMapper;
    private final JdOrderMapper jdOrderMapper;
    private final TmallOrderMapper tmallOrderMapper;
    private final AllOrderMapper allOrderMapper;

    public int create() {
        Date date = new Date();
        TOrder build = TOrder.builder()
                .amount(BigDecimal.TEN)
                .receiveName("Green")
                .receiveMobile("13718989888")
                .receiveAddress("北京朝阳区")
                // 未支付
                .status(1)
                .createTime(date)
                .createUser("Green")
                .updateTime(date)
                .updateUser("Green")
                .build();
        int i = orderMapper.insertSelective(build);
        log.info("i: {}", i);
        return i;

    }

    public List<TOrder> getOrder(Calendar now, int shardingToalCount, int shadingItem) {
        return orderMapper.getOrder(now.getTime(), shardingToalCount, shadingItem);
    }

    public void cancelOrder(Integer id, Date updateTime, int status, String updateUser, Date updateNow) {
        orderMapper.cancelOrder(id, updateTime, status, updateUser, updateNow);
    }

    public void produceThirdOrder() {
        Date date = new Date();
        for (int i = 0; i < 5; i++) {
            Random random = new Random();
            int nextInt = random.nextInt(2);
            if (nextInt == 0) {
                // 京东订单
                JdOrder jdOrder = JdOrder.builder()
                        .createTime(date)
                        .createUser("jdUser")
                        // 未抓取
                        .status(0)
                        .amount(BigDecimal.TEN)
                        .updateUser("jdUser")
                        .updateTime(date)
                        .build();
//                log.info("插入京东订单");
                jdOrderMapper.insertSelective(jdOrder);
            } else {
                // 天猫订单
                TmallOrder tmallOrder = TmallOrder.builder()
                        .createTime(date)
                        .createUser("tmallUser")
                        // 未抓取
                        .orderStatus(0)
                        .money(BigDecimal.TEN)
                        .updateUser("tmallUser")
                        .updateTime(date)
                        .build();
//                log.info("插入天猫订单");
                tmallOrderMapper.insertSelective(tmallOrder);
            }
        }
    }


    public List<JdOrder> getJdNotFetchedOrder(int cnt) {
        List<JdOrder> notFetchedOrder = jdOrderMapper.getNotFetchedOrder(cnt);
        return notFetchedOrder;
    }


    public List<TmallOrder> getTmallNotFetchedOrder(int cnt) {
        List<TmallOrder> notFetchedOrder = tmallOrderMapper.getNotFetchedOrder(cnt);
        return notFetchedOrder;
    }


    @Transactional
    public void processJdOrder(AllOrder allOrder) {
        allOrderMapper.insertSelective(allOrder);

        JdOrder jdOrder = JdOrder.builder()
                .id(allOrder.getThirdOrderId())
                // 已抓取
                .status(1)
                .updateUser("system")
                .updateTime(new Date())
                .build();
        jdOrderMapper.updateByPrimaryKey(jdOrder);
    }

    @Transactional
    public void processTmallOrder(AllOrder allOrder) {
        allOrderMapper.insertSelective(allOrder);

        TmallOrder tmallOrder = TmallOrder.builder()
                .id(allOrder.getThirdOrderId())
                // 已抓取
                .orderStatus(1)
                .updateUser("system")
                .updateTime(new Date())
                .build();
        tmallOrderMapper.updateByPrimaryKey(tmallOrder);
    }

}
