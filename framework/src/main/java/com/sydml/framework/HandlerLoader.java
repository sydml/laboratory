package com.sydml.framework;

import com.sydml.framework.aop.handler.AopHandler;
import com.sydml.framework.ioc.handler.BeanHandler;
import com.sydml.framework.ioc.handler.ClassHandler;
import com.sydml.framework.ioc.handler.ControllerHandler;
import com.sydml.framework.ioc.ioc.IocCore;
import com.sydml.framework.ioc.utils.ClassUtil;

/**
 * Created by Yuming-Liu
 * 日期： 2019-03-12
 * 时间： 22:11
 */
public final class HandlerLoader {
    public static void init(){
        Class<?>[] classList = {
                ClassHandler.class,
                BeanHandler.class,
                ControllerHandler.class,
                IocCore.class,
                AopHandler.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(), true);
        }
    }

}
