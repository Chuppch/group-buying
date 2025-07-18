package com.chuppch.types.design.framework.link.model2.chain;

import com.chuppch.types.design.framework.link.model2.handler.ILogicHandler;

/**
 * @author chuppch
 * @description 业务链路
 * @create 2025-05-13
 */
public class BusinessLinkedList<T, D, R> extends LinkedList<ILogicHandler<T, D, R>> implements ILogicHandler<T, D, R>{

    public BusinessLinkedList(String name) {
        super(name);
    }


    @Override
    // 重写 ILogicHandler 接口中的 apply 方法，实现责任链的处理逻辑。
    public R apply(T requestParameter, D dynamicContext) throws Exception {

        // 获取链表的第一个节点，用于遍历链表。
        Node<ILogicHandler<T, D, R>> current = this.first;

        // 使用 do-while 循环，确保至少执行一次（即链表不为空时总会处理第一个节点）。
        do {
            // 获取当前节点中的处理器（一个逻辑处理实现类，如 RuleLogic201）。
            ILogicHandler<T, D, R> item = current.item;

            // 调用当前处理器的 apply 方法，传入请求参数和上下文对象。
            // 每个处理器都可能返回一个结果（R 类型），也可能返回 null 表示继续下一个处理器。
            R apply = item.apply(requestParameter, dynamicContext);

            // 如果当前处理器返回的结果不为 null，说明处理完成，直接返回该结果，结束责任链执行。
            if (null != apply) return apply;

            // 当前处理器未处理（返回 null），则继续向链表中的下一个节点遍历。
            current = current.next;

            // 循环条件为当前节点不为 null，即链表还未遍历完。
        } while (null != current);

        // 如果所有处理器都未返回非 null 的结果，最终返回 null，表示链处理未命中任何处理逻辑。
        return null;
    }

}
