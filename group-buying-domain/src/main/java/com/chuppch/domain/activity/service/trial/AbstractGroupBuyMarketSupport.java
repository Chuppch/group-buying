package com.chuppch.domain.activity.service.trial;

import com.chuppch.domain.activity.adapter.repository.IActivityRepository;
import com.chuppch.types.design.framework.tree.AbstractMultiThreadStrategyRouter;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @description: 抽象的拼团营销支撑类
 * @author: chuppch
 * @create: 2025-04-15
 **/
public abstract class AbstractGroupBuyMarketSupport<MarketProductEnity, DynamicContext,TrialBalanceEntity > extends AbstractMultiThreadStrategyRouter<MarketProductEnity, DynamicContext, TrialBalanceEntity> {

    protected long timeout = 500;

    @Resource
    protected IActivityRepository repository;

    @Override
    protected void multiTread(MarketProductEnity requestParameter, DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {
        //缺省的实现 避免不需要实现此方法的结点重复的出现
    }
}
