package com.chuppch.infrastructure.dao;

import com.chuppch.infrastructure.dao.po.GroupBuyOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @author chuppch
 * @description 营销首页服务
 * @create 2025-05-24
 */
@Mapper
public interface IGroupBuyOrderDao {

    void insert(GroupBuyOrder order);

    int updateAddLockCount(String teamId);// 根据teamId更新添加加锁数量

    int updateSubtractionLockCount(String tramId);// 根据teamId更新减少的锁数量

    GroupBuyOrder queryGroupBuyProgress(String teamId);// 根据teamId查询团购进度

    GroupBuyOrder queryGroupBuyTeamByTeamId(String teamId);//根据团队ID查询团购订单信息。

    int updateAddCompleteCount(String teamId); // 根据团队ID更新增加已完成的数量。 todo

    int updateOrderStatus2COMPLETE(String teamId); // 根据团队ID将订单状态更新为已完成 todo

    List<GroupBuyOrder> queryGroupBuyProgressByTeamIds(Set<String> teamIds); // 根据多个团队ID查询对应的拼团进度信息（目标人数、完成情况、拼团时间等）

    Integer queryAllTeamCount(Set<String> teamIds); // 根据团队ID集合统计拼团总数（不区分是否完成）

    Integer queryAllTeamCompleteCount(Set<String> teamIds); // 根据团队ID集合统计已完成的拼团队伍数量（status = 1）

    Integer queryAllUserCount(Set<String> teamIds); // 根据团队ID集合统计所有拼团的参与用户数量（汇总所有团队的lock_count）

}
