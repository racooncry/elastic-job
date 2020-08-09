package com.shenfeng.yxw.elasticjob.domain.entity.jdOrder;

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
@Table(name = "jd_order")
public class JdOrder {
    @Id
    private Integer id;

    private Integer status;

    private BigDecimal amount;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_user")
    private String updateUser;

    @Column(name = "update_time")
    private Date updateTime;
}