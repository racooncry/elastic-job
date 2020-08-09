package com.shenfeng.yxw.elasticjob.domain.entity.order;

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
@Table(name = "t_order")
public class TOrder {
    @Id
    private Integer id;

    private BigDecimal amount;

    private Integer status;

    @Column(name = "receive_name")
    private String receiveName;

    @Column(name = "receive_address")
    private String receiveAddress;

    @Column(name = "receive_mobile")
    private String receiveMobile;

    @Column(name = "receive_user")
    private String receiveUser;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "update_user")
    private String updateUser;

    @Column(name = "update_time")
    private Date updateTime;
}