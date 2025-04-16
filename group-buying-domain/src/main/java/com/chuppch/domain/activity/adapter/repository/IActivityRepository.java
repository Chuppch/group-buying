package com.chuppch.domain.activity.adapter.repository;


import com.chuppch.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.chuppch.domain.activity.model.valobj.SkuVO;

/**
 * @author chuppch
 * @description 活动仓储
 * @create 2025-4-16
 */
public interface IActivityRepository {

    GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(String source,String channel);

    SkuVO querySkuByGoodsId(String goodsId);

}
