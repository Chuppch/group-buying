package com.chuppch.infrastructure.dao;

import com.chuppch.infrastructure.dao.po.GroupBuyOrderList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
}
