package com.chuppch.types.design.framework.link.model2.handler;

/**
 * @author chuppch
 *  * @description 逻辑处理器
 * @date 2025-5-13
 */
public interface ILogicHandler<T, D, R> {

    default R next(T requestParameter, D dynamicContext) {
        return null;
    }

    R apply(T requestParameter, D dynamicContext) throws Exception;

}