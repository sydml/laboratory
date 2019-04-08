package com.sydml.authorization.job.domain;

/**
 * @author Liuym
 * @date 2019/4/8 0008
 */
public class DynamicJob1 implements Runnable {
    @Override
    public void run() {
        System.out.println("another job run ...");
    }
}
