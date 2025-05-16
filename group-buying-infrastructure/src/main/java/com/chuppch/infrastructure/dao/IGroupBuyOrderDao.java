package com.chuppch.infrastructure.dao;

import com.chuppch.infrastructure.dao.po.GroupBuyOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IGroupBuyOrderDao {

    void insert(GroupBuyOrder order);

    int updateAddLockCount(String teamId);// 根据teamId更新添加加锁数量

    int updateSubtractionLockCount(String tramId);// 根据teamId更新减少的锁数量

    GroupBuyOrder queryGroupBuyProgress(String teamId);// 根据teamId查询团购进度

    GroupBuyOrder queryGroupBuyTeamByTeamId(String teamId);//根据团队ID查询团购订单信息。

    int updateAddCompleteCount(String teamId); // 根据团队ID更新增加已完成的数量。

    int updateOrderStatus2COMPLETE(String teamId); // 根据团队ID将订单状态更新为已完成
}
