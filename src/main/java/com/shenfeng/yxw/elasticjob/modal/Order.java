package com.shenfeng.yxw.elasticjob.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private Integer orderId;
    // 0 未处理，1 处理
    private Integer status;
}
