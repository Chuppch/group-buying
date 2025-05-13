package com.chuppch.types.design.framework.link.model1;

/**
 * @author chuppch
 * @description 略规则责任链接口
 * @create 2025-05-14
 */
public interface ILogicLink<T, D, R> extends ILogicChainArmory<T, D, R> {

    R apply(T requestParameter, D dynamicContext) throws Exception;

}

