package com.chuppch.domain.trade.adapter.repository;

import com.chuppch.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import com.chuppch.domain.trade.model.aggregate.GroupBuyTeamSettlementAggregate;
import com.chuppch.domain.trade.model.entity.GroupBuyActivityEntity;
import com.chuppch.domain.trade.model.entity.GroupBuyTeamEntity;
import com.chuppch.domain.trade.model.entity.MarketPayOrderEntity;
import com.chuppch.domain.trade.model.entity.NotifyTaskEntity;
import com.chuppch.domain.trade.model.valobj.GroupBuyProgressVO;

import java.util.List;

/**
 * @author chuppch
 * @description 交易仓储服务接口
 * @create 2025-05-22
 */
public interface ITradeRepository {

    MarketPayOrderEntity queryMarketPayOrderEntityByOutTradeNo(String userId, String outTradeNo);

    MarketPayOrderEntity lockMarketPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate);

    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    GroupBuyActivityEntity queryGroupBuyActivityEntityByActivityId(Long activityId);

    Integer queryOrderCountByActivityId(Long activity, String userID);

    GroupBuyTeamEntity queryGroupBuyTeamByTeamId(String teamId);

    boolean settlementMarketPayOrder(GroupBuyTeamSettlementAggregate groupBuyTeamSettlementAggregate);

    boolean isSCBlackIntercept(String source, String channel);

    List<NotifyTaskEntity> queryUnExecutedNotifyTaskList();

    List<NotifyTaskEntity> queryUnExecutedNotifyTaskList(String teamId);

    int updateNotifyTaskStatusSuccess(String teamId);

    int updateNotifyTaskStatusError(String teamId);

    int updateNotifyTaskStatusRetry(String teamId);

}
