package com.chuppch.domain.activity.service.discount;

import com.chuppch.domain.activity.model.valobj.DiscountTypeEnum;
import com.chuppch.domain.activity.model.valobj.GroupBuyActivityDiscountVO;

import java.math.BigDecimal;

/**
 * @author chuppch
 * @description 折扣计算服务抽象类
 * @create 2025-4-17
 */
public abstract class AbstractDiscountCalculateService implements IDiscountCalculateService {

    @Override
    public BigDecimal calculate(String userId, BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        //1.人群标签过滤
        if (DiscountTypeEnum.TAG.equals(groupBuyDiscount.getDiscountType())){
            boolean isCrowdRange = filterTaged(userId,groupBuyDiscount.getTagId());
            if (!isCrowdRange) return originalPrice;
        }
        //2.折扣优惠计算
        return doCalculate(originalPrice,groupBuyDiscount);
    }

    //人群过滤 - 限定人群优惠
    private boolean filterTaged(String userId, String tagId) {
        //TODO 后续开发
        return true;
    }

    protected abstract BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount);

}
