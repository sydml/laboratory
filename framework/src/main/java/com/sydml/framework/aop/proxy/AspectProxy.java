package com.sydml.framework.aop.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by Yuming-Liu
 * 日期： 2019-03-12
 * 时间： 23:08
 */
public abstract class AspectProxy implements Proxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    /**
     * 代理目标对象执行代替执行method
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;

        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();
        begin();
        try {
            if (intercept(cls, method, params)) {
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params, result);
            }else{
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            LOGGER.error("proxy failure", e);
            error(cls, method, params, e);
            throw e;
        }finally {
            end();
        }
        return result;

    }



    public void begin() {

    }
    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable{
        return true;
    }
    public void before(Class<?> cls, Method method, Object[] params)throws Throwable{

    }

    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable{

    }

    private void error(Class<?> cls, Method method, Object[] params, Exception e) throws Throwable{
    }

    public void end(){

    }

}
