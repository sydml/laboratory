package com.sydml.framework.helper;

import com.sydml.framework.annotation.Component;
import com.sydml.framework.annotation.Controller;
import com.sydml.framework.config.helper.ConfigHelper;
import com.sydml.framework.utils.ClassUtil;
import com.sydml.framework.annotation.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Liuym
 * @date 2019/3/10 0010
 */
public final class ClassHelper {
    private static final Set<Class<?>> CLASS_SET;

    static{
        String basePackage = ConfigHelper.getBasePackages();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * 获取service类
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取controller类
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取Component类
     *//*
    public static Set<Class<?>> getComponentClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Component.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }*/

    /**
     * 获取所有的bean类
     * @return
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getControllerClassSet());
        beanClassSet.addAll(getServiceClassSet());
//        beanClassSet.addAll(getComponentClassSet());
        return beanClassSet;
    }

}
