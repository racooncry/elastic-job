package com.shenfeng.yxw.elasticjob.domain.entity.allOrder;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "all_order")
public class AllOrder {
    @Id
    private Integer id;

    @Column(name = "third_order_id")
    private Integer thirdOrderId;

    /**
     * 0京东，1天猫
     */
    private Integer type;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_user")
    private String updateUser;

    @Column(name = "update_time")
    private Date updateTime;
}