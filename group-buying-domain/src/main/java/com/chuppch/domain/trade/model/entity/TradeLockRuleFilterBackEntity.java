package com.chuppch.domain.trade.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chuppch
 * @description 拼团交易，过滤反馈实体
 * @create 2025-05-15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeLockRuleFilterBackEntity {
    //用户参与活动的订单量
    private Integer userTakeOrderCount;

}
