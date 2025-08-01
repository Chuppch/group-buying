package com.chuppch.domain.trade.service.lock.factory;

import com.chuppch.domain.trade.model.entity.GroupBuyActivityEntity;
import com.chuppch.domain.trade.model.entity.TradeLockRuleCommandEntity;
import com.chuppch.domain.trade.model.entity.TradeLockRuleFilterBackEntity;
import com.chuppch.domain.trade.service.lock.filter.ActivityUsabilityRuleFilter;
import com.chuppch.domain.trade.service.lock.filter.UserTakeLimitRuleFilter;
import com.chuppch.types.design.framework.link.model2.LinkArmory;
import com.chuppch.types.design.framework.link.model2.chain.BusinessLinkedList;
import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @author chuppch
 * @description 交易规则过滤工厂
 * @create 2025-05-15
 */
@Service
public class TradeLockRuleFilterFactory {


    @Bean("tradeRuleFiter")
    public BusinessLinkedList<TradeLockRuleCommandEntity, TradeLockRuleFilterFactory.DynamicContext, TradeLockRuleFilterBackEntity> tradeRuleFilter(ActivityUsabilityRuleFilter activityUsabilityRuleFilter, UserTakeLimitRuleFilter userTakeLimitRuleFilter) {
        //组装链
        LinkArmory<TradeLockRuleCommandEntity, TradeLockRuleFilterFactory.DynamicContext, TradeLockRuleFilterBackEntity> linkArmory =
                new LinkArmory<>("交易规则过滤连", activityUsabilityRuleFilter, userTakeLimitRuleFilter);

        // 链对象
        return linkArmory.getLogicLink();
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {

        public GroupBuyActivityEntity groupBuyActivity;

    }

}
