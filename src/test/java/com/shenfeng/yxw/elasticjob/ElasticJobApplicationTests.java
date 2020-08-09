package com.shenfeng.yxw.elasticjob;

import com.shenfeng.yxw.elasticjob.domain.entity.order.TOrder;
import com.shenfeng.yxw.elasticjob.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
class ElasticJobApplicationTests {
    private final OrderService orderService;

    //    @Test
    void create() {
        orderService.create();
    }

    @Test
    void contextLoads() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, -30);

        List<TOrder> list = orderService.getOrder(instance, 2, 1);
        List<Integer> integers = list.stream().map(TOrder::getId).collect(Collectors.toList());
        log.info("ids:{}", integers);
    }


}
