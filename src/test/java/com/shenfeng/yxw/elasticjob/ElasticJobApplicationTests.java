package com.shenfeng.yxw.elasticjob;

import com.shenfeng.yxw.elasticjob.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ElasticJobApplicationTests {
    private final OrderService orderService;
    @Test
    void contextLoads() {
        orderService.create();
    }


}
