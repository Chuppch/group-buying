package com.chuppch.test.types.rule01.logic;

import com.chuppch.test.types.rule02.factory.Rule02TradeRuleFactory;
import com.chuppch.types.design.framework.link.model1.AbstractLogicLink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RuleLogic102 extends AbstractLogicLink<String, Rule02TradeRuleFactory.DynamicContext, String> {
    @Override
    public String apply(String requestParameter, Rule02TradeRuleFactory.DynamicContext dynamicContext) throws Exception {

        log.info("link model01 RuleLogic102");

        return "link model01 单实例链";
    }
}
