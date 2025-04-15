package com.chuppch.domain.activity.service.trial.node;


import com.chuppch.domain.activity.model.entity.TrialBalanceEntity;
import com.chuppch.domain.activity.model.entity.MarketProductEntity;
import com.chuppch.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import com.chuppch.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import com.chuppch.types.design.framework.tree.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author chuppch
 * @date 2025-04-15
 * @des 根结点
 */
@Slf4j
@Service
public class RootNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {


    @Override
    public TrialBalanceEntity apply(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {



        return null;
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return null;
    }
}
