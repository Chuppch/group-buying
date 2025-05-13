package com.chuppch.types.design.framework.link.model1;
/**
 * @author chuppch
 * @description 抽象类
 * @create 2025-05-14
 */
public abstract class AbstractLogicLink<T, D, R> implements ILogicLink<T, D, R> {

    // 定义当前节点的下一个逻辑节点（即链上的下一个处理器）。
    private ILogicLink<T, D, R> next;

    // 获取下一个链路节点。用于遍历责任链。
    @Override
    public ILogicLink<T, D, R> next() {
        return next;
    }

    // 设置下一个链路节点。返回传入的节点对象本身，用于链式调用（builder 模式）。
    @Override
    public ILogicLink<T, D, R> appendNext(ILogicLink<T, D, R> next) {
        this.next = next;     // 将当前节点的 next 指向传入的下一个处理节点
        return next;          // 返回 next 本身，方便链式拼接多个节点
    }

    // 该方法用于在当前节点内部继续调用责任链中的下一个节点的 apply 方法。
    // 这是责任链“向下传递”的关键逻辑。
    protected R next(T requestParameter, D dynamicContext) throws Exception {
        // 调用下一个节点的 apply 方法，将当前请求参数和上下文继续传递
        return next.apply(requestParameter, dynamicContext);
    }

}
