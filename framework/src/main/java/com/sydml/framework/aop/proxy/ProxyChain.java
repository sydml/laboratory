package com.sydml.framework.aop.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuming-Liu
 * 日期： 2019-03-12
 * 时间： 22:57
 */
public class ProxyChain {

    private final Class<?> targetClass;

    private final Object targetObject;

    private final Method targetMethod;

    private final MethodProxy methodProxy;

    private final Object[] methodParams;

    private List<Proxy> proxyList = new ArrayList<>();

    private int proxyIndex = 0;

    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public MethodProxy getMethodProxy() {
        return methodProxy;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Object doProxyChain() throws Throwable{
        Object methodResutl;
        if (proxyIndex < proxyList.size()) {
            methodResutl = proxyList.get(proxyIndex++).doProxy(this);
        }else{
            methodResutl = methodProxy.invokeSuper(targetObject,methodParams);
        }
        return methodResutl;
    }
}
