package com.shenfeng.yxw.elasticjob.dao.order;

import com.shenfeng.yxw.elasticjob.domain.entity.order.TOrder;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

public interface TOrderMapper extends Mapper<TOrder> {
    List<TOrder> getOrder(Date time, int shadingItem, int shadingItem1);

    int cancelOrder(Integer id, Date updateTime, int status, String updateUser, Date updateNow);
}