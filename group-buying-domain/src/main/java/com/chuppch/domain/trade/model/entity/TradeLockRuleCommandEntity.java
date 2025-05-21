package com.chuppch.domain.trade.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chuppch
 * @description 拼团交易命令实体
 * @create 2025-05-15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeLockRuleCommandEntity {

    //用户id
    private String userId;
    //活动ID
    private Long activityId;

}
