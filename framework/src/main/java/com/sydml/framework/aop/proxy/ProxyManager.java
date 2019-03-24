package com.sydml.framework.aop.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 创建代理对象
 * Created by Yuming-Liu
 * 日期： 2019-03-12
 * 时间： 23:04
 */
public class ProxyManager {
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy proxy) throws Throwable {
                return new ProxyChain(targetClass, targetObject, targetMethod, proxy, methodParams, proxyList).doProxyChain();
            }
        });
    }
}
