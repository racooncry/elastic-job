package com.shenfeng.yxw.elasticjob.domain.entity.tmallOrder;

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
@Table(name = "tmall_order")
public class TmallOrder {
    @Id
    private Integer id;

    @Column(name = "order_status")
    private Integer orderStatus;

    private BigDecimal money;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_user")
    private String updateUser;

    @Column(name = "update_time")
    private Date updateTime;
}