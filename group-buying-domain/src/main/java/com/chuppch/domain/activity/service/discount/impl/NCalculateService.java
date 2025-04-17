package com.chuppch.domain.activity.service.discount.impl;


import com.chuppch.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.chuppch.domain.activity.service.discount.AbstractDiscountCalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author chuppch
 * @date 2025-4-17
 * @desc: N元购
 */
@Slf4j
@Service("N")
public class NCalculateService extends AbstractDiscountCalculateService {

    @Override
    protected BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        log.info("满N元优惠计算:{}", groupBuyDiscount.getDiscountType().getInfo());

        //折扣表达式 - 直接为优惠后的价格
        String marketExpr = groupBuyDiscount.getMarketExpr();
        //n元购
        return new BigDecimal(marketExpr);
    }
}
