package com.sydml.transaction;

/**
 * @author Liuym
 * @date 2019/5/27 0027
 */
public class C extends B {
    @Override
    public void say() {
        System.out.println("c say");
        super.say();
    }

    public static void main(String[] args) {
        C c = new C();
        c.say();
    }
}
