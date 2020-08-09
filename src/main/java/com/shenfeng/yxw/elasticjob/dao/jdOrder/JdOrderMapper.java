package com.shenfeng.yxw.elasticjob.dao.jdOrder;

import com.shenfeng.yxw.elasticjob.domain.entity.jdOrder.JdOrder;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface JdOrderMapper extends Mapper<JdOrder> {
    List<JdOrder> getNotFetchedOrder(int cnt);
}