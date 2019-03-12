package com.sydml.framework.ioc.handler;

import java.lang.reflect.Method;

/**
 * Created by Yuming-Liu
 * 日期： 2019-03-12
 * 时间： 21:52
 */
public class Handler {
    private Class<?> controllerClass;

    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
