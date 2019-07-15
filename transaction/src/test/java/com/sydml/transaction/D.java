package com.sydml.transaction;

/**
 * @author Liuym
 * @date 2019/5/27 0027
 */
public class D extends AbstractA {
    @Override
    public void say() {
        super.test();
    }

    public static void main(String[] args) {
        D d = new D();
        d.say();

    }
}
