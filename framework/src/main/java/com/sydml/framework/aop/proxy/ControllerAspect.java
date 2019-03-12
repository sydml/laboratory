package com.sydml.framework.aop.proxy;

import com.sydml.framework.aop.annotations.Aspect;
import com.sydml.framework.ioc.annotation.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by Yuming-Liu
 * 日期： 2019-03-12
 * 时间： 23:19
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);
    private long begin;

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        LOGGER.debug("-------------begin--------------");
        LOGGER.debug(String.format("class:%s", cls.getName()));
        LOGGER.debug(String.format("method:%s", method.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
        LOGGER.debug(String.format("time:%dms", System.currentTimeMillis() - begin));
        LOGGER.debug("----------------end-------------");
    }
}
