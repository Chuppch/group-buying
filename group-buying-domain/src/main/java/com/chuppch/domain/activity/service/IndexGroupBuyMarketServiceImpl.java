package com.chuppch.domain.activity.service;

import com.chuppch.domain.activity.model.entity.MarketProductEntity;
import com.chuppch.domain.activity.model.entity.TrialBalanceEntity;
import com.chuppch.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import com.chuppch.types.design.framework.tree.StrategyHandler;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chuppch
 * @date 2022/11/01
 * @describe 首页营销服务接口
 */
@Service
public class IndexGroupBuyMarketServiceImpl implements IIndexGroupBuyMarketService {


    @Resource
    private DefaultActivityStrategyFactory defaultActivityStrategyFactory;

    @Override
    public TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProductEntity) throws Exception {

        StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> strategyHandler = defaultActivityStrategyFactory.strategyHandler();

        TrialBalanceEntity trialBalanceEntity = strategyHandler.apply(marketProductEntity, new DefaultActivityStrategyFactory.DynamicContext());

        return trialBalanceEntity;
    }
}
