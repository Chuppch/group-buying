package com.chuppch.types.design.framework.tree;

/**
 * @author chuppch
 * @date 2025/04/15
 * T 入参类型
 * D 上下文参数
 * R 返参类型
 * @desc 策略处理器
 */
public interface StrategyHandler<T,D,R> {

    StrategyHandler DEFAULT = (T, D) ->null;

    R apply(T requestParameter, D dynamicContext) throws Exception;
}
