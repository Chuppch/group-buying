package com.chuppch.types.design.framework.tree;
/**
 * @author chuppch
 * @date 2025/04/15
 * T 入参类型
 * D 上下文参数
 * R 返参类型
 * @desc 策略映射器
 */
public interface StrategyMapper<T, D, R> {

    /**
     * 获取策略处理器
     * @param requestParameter 入参
     * @param dynamicContext 上下文
     * @return 返参
     */
    StrategyHandler<T, D, R> get(T requestParameter,D dynamicContext) throws Exception;

}
