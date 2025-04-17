package com.chuppch.domain.activity.service.discount.impl;

import com.chuppch.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.chuppch.domain.activity.service.discount.AbstractDiscountCalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author: chuppch
 * @date: 2025-4-17
 * @description: 满减优惠券
 */
@Slf4j
@Service("MJ")
public class MJCalculateService extends AbstractDiscountCalculateService {

    @Override
    protected BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        log.info("满减优惠计算:{}", groupBuyDiscount.getDiscountType().getInfo());

        //折扣表达式
        String marketExpr = groupBuyDiscount.getMarketExpr();
        String[] split = marketExpr.split(",");
        BigDecimal x = new BigDecimal(split[0].trim());
        BigDecimal y = new BigDecimal(split[1].trim());

        //不满足最低满减约束，则按照原价
        if (originalPrice.compareTo(x) < 0) {
            return originalPrice;
        }

        //扣减价格
        BigDecimal deductionPrice = originalPrice.subtract(y);

        //判断扣减后金额，最低支付1分钱
        if (deductionPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return new BigDecimal("0.01");
        }

        return deductionPrice;
    }
}
