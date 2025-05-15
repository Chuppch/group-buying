package com.chuppch.domain.trade.service.factory;

import com.chuppch.domain.trade.model.entity.GroupBuyActivityEntity;
import com.chuppch.domain.trade.model.entity.TradeRuleCommandEntity;
import com.chuppch.domain.trade.model.entity.TradeRuleFilterBackEntity;
import com.chuppch.domain.trade.service.filter.ActivityUsabilityRuleFilter;
import com.chuppch.domain.trade.service.filter.UserTakeLimitRuleFilter;
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
public class TradeRuleFilterFactory {


    @Bean("tradeRuleFiter")
    public BusinessLinkedList<TradeRuleCommandEntity, TradeRuleFilterFactory.DynamicContext, TradeRuleFilterBackEntity> tradeRuleFilter(ActivityUsabilityRuleFilter activityUsabilityRuleFilter, UserTakeLimitRuleFilter userTakeLimitRuleFilter) {
        //组装链
        LinkArmory<TradeRuleCommandEntity, TradeRuleFilterFactory.DynamicContext, TradeRuleFilterBackEntity> linkArmory =
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
