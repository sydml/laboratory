package com.sydml.transaction;

/**
 * @author Liuym
 * @date 2019/5/27 0027
 */
public abstract class AbstractA {
    public AbstractA() {
        System.out.println("AbstractA constructor");
    }

    public abstract void say();

    public void test() {
        System.out.println("aba test");
    }
}
