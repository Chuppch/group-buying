package com.chuppch.domain.activity.adapter.repository;


import com.chuppch.domain.activity.model.entity.UserGroupBuyOrderDetailEntity;
import com.chuppch.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.chuppch.domain.activity.model.valobj.SCSkuActivityVO;
import com.chuppch.domain.activity.model.valobj.SkuVO;
import com.chuppch.domain.activity.model.valobj.TeamStatisticVO;
import com.chuppch.domain.trade.model.entity.NotifyTaskEntity;

import java.util.List;

/**
 * @author chuppch
 * @description 活动仓储
 * @create 2025-4-16
 */
public interface IActivityRepository {

    GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(Long activityId);

    SkuVO querySkuByGoodsId(String goodsId);

    SCSkuActivityVO querySkuActivityByGoodsId(String source, String channel, String goodsId);

    boolean isTagCrowdRange(String tagId, String userId);

    boolean downgradeSwitch();

    boolean cutRange(String userId);

    // 查询指定用户在指定活动中参与的进行中的拼团订单明细，用于展示“我的拼团”列表（限制返回条数）
    List<UserGroupBuyOrderDetailEntity> queryInProgressUserGroupBuyOrderDetailListByOwner(Long activityId, String userId, Integer ownerCount);

    // 查询指定活动中除当前用户外的其他正在进行中的拼团队伍明细，用于推荐可加入的拼团（限制返回条数）
    List<UserGroupBuyOrderDetailEntity> queryInProgressUserGroupBuyOrderDetailListByRandom(Long activityId, String userId, Integer randomCount);

    // 根据活动ID统计拼团整体数据：拼团总数、已完成拼团数、参与用户总数
    TeamStatisticVO queryTeamStatisticByActivityId(Long activityId);

}
