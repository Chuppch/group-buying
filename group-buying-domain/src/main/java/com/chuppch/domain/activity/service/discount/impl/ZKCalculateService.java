package com.chuppch.domain.activity.service.discount.impl;

import com.chuppch.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.chuppch.domain.activity.service.discount.IDiscountCalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author chuppch
 * @desc 折扣
 * @create 2025-4-17
 */
@Slf4j
@Service("ZK")
public class ZKCalculateService implements IDiscountCalculateService {
    @Override
    public BigDecimal calculate(String userId, BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        log.info("折扣优惠计算:{}", groupBuyDiscount.getDiscountType().getInfo());

        //折扣表达式 - 折扣百分比
        String marketExpr = groupBuyDiscount.getMarketExpr();

        //折扣后价格
        BigDecimal discountPrice = originalPrice.multiply(new BigDecimal(marketExpr));

        //判断折扣后金额，最低支持一分钱
        if (discountPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return new BigDecimal("0.01");
        }

        return discountPrice;
    }
}
