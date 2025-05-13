package com.chuppch.types.design.framework.link.model2;

import com.chuppch.types.design.framework.link.model2.chain.BusinessLinkedList;
import com.chuppch.types.design.framework.link.model2.handler.ILogicHandler;

/**
 * @author chuppch
 * @description 链路装配
 * @create 2025-05-14
 */
public class LinkArmory<T, D, R> {

    // 定义一个业务链对象，用来串联多个 ILogicHandler 实现类。
    private final BusinessLinkedList<T, D, R> logicLink;

    @SafeVarargs
    // 构造方法，接收一个链名称和多个逻辑处理器（通过可变参数传入）。
    public LinkArmory(String linkName, ILogicHandler<T, D, R>... logicHandlers) {
        // 创建一个 BusinessLinkedList 实例，用于管理处理器链。
        logicLink = new BusinessLinkedList<>(linkName);

        // 遍历所有传入的处理器，将其添加到业务链中。
        for (ILogicHandler<T, D, R> logicHandler : logicHandlers) {
            logicLink.add(logicHandler);
        }
    }

    // 提供一个 getter 方法，用于外部获取已经构建好的业务处理链。
    public BusinessLinkedList<T, D, R> getLogicLink() {
        return logicLink;
    }

}

