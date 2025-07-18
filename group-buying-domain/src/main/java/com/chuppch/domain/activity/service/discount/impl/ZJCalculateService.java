package com.chuppch.domain.activity.service.discount.impl;


import com.chuppch.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.chuppch.domain.activity.service.discount.IDiscountCalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author: chuppch
 * @date: 2023/1/31 15:04
 * @desc: 直降
 */
@Slf4j
@Service("ZJ")
public class ZJCalculateService implements IDiscountCalculateService {


    @Override
    public BigDecimal calculate(String userId, BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        log.info("折扣优惠计算:{}", groupBuyDiscount.getDiscountType().getInfo());
       // log.info("折扣优惠计算:{}", groupBuyDiscount.getDiscountType().getCode());

        //折扣表达式子
        String marketExpr = groupBuyDiscount.getMarketExpr();

        //折扣价格
        BigDecimal discountPrice = originalPrice.subtract(new BigDecimal(marketExpr));

        //判断折扣后金额，最低支持一分钱
        if (discountPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return new BigDecimal("0.01");
        }

        return discountPrice;
    }
}
