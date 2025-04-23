package com.chuppch.domain.activity.adapter.repository;


import com.chuppch.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.chuppch.domain.activity.model.valobj.SCSkuActivityVO;
import com.chuppch.domain.activity.model.valobj.SkuVO;

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

}
