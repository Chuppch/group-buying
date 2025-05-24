package com.chuppch.infrastructure.dao;

import com.chuppch.infrastructure.dao.po.GroupBuyOrderList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author chuppch
 * @description 用户拼单
 * @create 2025-05-24
 */
@Mapper
public interface IGroupBuyOrderListDao {

    // 新增拼团订单记录 用于记录用户参与某拼团活动的下单行为
    void insert(GroupBuyOrderList groupBuyOrderListReq);

    // 根据外部交易单号（outTradeNo）查询对应的拼团订单记录，常用于支付后订单校验或回调处理
    GroupBuyOrderList queryGroupBuyOrderRecordByOutTradeNo(GroupBuyOrderList groupBuyOrderListReq);

    // 根据活动ID统计该活动下已成功下单的拼团订单数量，用于拼团成团逻辑判断
    Integer queryOrderCountByActivityId(GroupBuyOrderList groupBuyOrderListReq);

    // 将指定拼团订单状态更新为“已完成（COMPLETE）”，用于成团后订单状态的推进
    int updateOrderStatus2COMPLETE(GroupBuyOrderList groupBuyOrderListReq);

    // 根据拼团队伍ID查询所有已完成状态的拼团订单对应的外部交易号列表，用于批量处理发货、回调等业务
    List<String> queryGroupBuyCompleteOrderOutTradeNoListByTeamId(String teamId);

    List<GroupBuyOrderList> queryInProgressUserGroupBuyOrderDetailListByUserId(GroupBuyOrderList groupBuyOrderListReq); // 根据用户ID和活动ID查询用户参与的正在进行中的拼团订单明细（不包括已完成、已失效的订单）

    List<GroupBuyOrderList> queryInProgressUserGroupBuyOrderDetailListByRandom(GroupBuyOrderList groupBuyOrderListReq); // 查询除当前用户外，正在进行中的拼团订单明细，用于随机推荐可加入的拼团队伍

    List<GroupBuyOrderList> queryInProgressUserGroupBuyOrderDetailListByActivityId(Long activityId); // 根据活动ID查询该活动下所有正在进行中的拼团队伍（按teamId分组）

}
