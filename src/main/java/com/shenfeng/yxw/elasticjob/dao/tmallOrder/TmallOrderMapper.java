package com.shenfeng.yxw.elasticjob.dao.tmallOrder;

import com.shenfeng.yxw.elasticjob.domain.entity.tmallOrder.TmallOrder;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TmallOrderMapper extends Mapper<TmallOrder> {
    List<TmallOrder> getNotFetchedOrder(int cnt);
}