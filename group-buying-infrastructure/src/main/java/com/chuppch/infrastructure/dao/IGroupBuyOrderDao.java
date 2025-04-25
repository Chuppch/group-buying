package com.chuppch.infrastructure.dao;

import com.chuppch.infrastructure.dao.po.GroupBuyOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IGroupBuyOrderDao {

    void insert(GroupBuyOrder order);

    int updateAddLockCount(String teamId);// 根据teamId更新添加加锁数量

    int updateSubtractionLockCount(String tramId);// 根据teamId更新减少的锁数量

    GroupBuyOrder queryGroupBuyProgress(String teamId);// 根据teamId查询团购进度

}
