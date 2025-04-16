package com.chuppch.types.design.framework.tree;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 异步资源加载策略
 * @create 2025-4-16
 */
public abstract class AbstractMultiThreadStrategyRouter<T, D, R> implements StrategyHandler<T, D, R>, StrategyMapper<T, D, R> {

    @Getter
    @Setter
    protected StrategyHandler<T, D, R> defaultStrategyHandler = StrategyHandler.DEFAULT;

    public R router(T requestParameter, D dynamicContext) throws Exception {
        StrategyHandler<T, D, R> strategyHandler = get(requestParameter, dynamicContext);
        if (strategyHandler != null) return strategyHandler.apply(requestParameter, dynamicContext);
        return defaultStrategyHandler.apply(requestParameter, dynamicContext);
    }


    @Override
    public R apply(T requestParameter, D dynamicContext) throws Exception {
        //异步加载数据
        multiTread(requestParameter, dynamicContext);
        //业务流程受理
        return doApply(requestParameter, dynamicContext);
    }

    /**
     * 多线程加载数据
     *
     * @param requestParameter
     * @param dynamicContext
     */
    protected abstract void multiTread(T requestParameter, D dynamicContext) throws ExecutionException, InterruptedException, TimeoutException;

    /**
     * 业务流程受理
     *
     * @param requestParameter
     * @param dynamicContext
     * @return
     */
    protected abstract R doApply(T requestParameter, D dynamicContext) throws Exception;
}