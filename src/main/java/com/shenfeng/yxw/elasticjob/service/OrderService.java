package com.shenfeng.yxw.elasticjob.service;

import com.shenfeng.yxw.elasticjob.dao.order.TOrderMapper;
import com.shenfeng.yxw.elasticjob.domain.entity.order.TOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService {

    private final TOrderMapper orderMapper;

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


}
