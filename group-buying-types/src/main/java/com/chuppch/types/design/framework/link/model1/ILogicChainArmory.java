package com.chuppch.types.design.framework.link.model1;

/**
 * @author chuppch
 * @description 责任链装配
 * @create 2025-05-14
 */
public interface ILogicChainArmory<T, D, R> {

    ILogicLink<T, D, R> next();

    ILogicLink<T, D, R> appendNext(ILogicLink<T, D, R> next);

}
