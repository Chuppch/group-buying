package com.chuppch.domain.activity.service.discount;

import com.chuppch.domain.activity.model.valobj.GroupBuyActivityDiscountVO;

import java.math.BigDecimal;

/**
 * 折扣计算服务
 * @author chuppch
 * @date 2025-4-17
 * @desc 折扣计算服务
 */
public interface IDiscountCalculateService {

    /**
     * 折扣计算
     * @param userId 用户ID
     * @param originalPrice 初始价格
     * @param groupBuyDiscount 折扣计划配置
     * @return 商品优惠后价格
     */
    BigDecimal calculate(String userId, BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount);

}
