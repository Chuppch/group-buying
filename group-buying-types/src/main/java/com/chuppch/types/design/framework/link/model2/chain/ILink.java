package com.chuppch.types.design.framework.link.model2.chain;

/**
 * @author chuppch
 * @description 链接口
 * @create 2025-05-13
 */
public interface ILink<E> {

    boolean add(E e);

    boolean addFirst(E e);

    boolean addLast(E e);

    boolean remove(Object o);

    E get(int index);

    void printLinkList();
}
