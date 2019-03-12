package com.sydml.framework.aop.proxy;

/**
 * Created by Yuming-Liu
 * 日期： 2019-03-12
 * 时间： 22:56
 */
public interface Proxy {
    /**
     * 执行链式代理
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
