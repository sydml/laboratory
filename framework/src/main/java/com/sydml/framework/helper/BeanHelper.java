package com.sydml.framework.helper;

import com.sydml.framework.utils.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Liuym
 * @date 2019/3/10 0010
 */
public final class BeanHelper {
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();
    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass : beanClassSet) {
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, obj);
        }
    }

    /**
     * 获取bean 映射
      * @return
     */
    public static Map<Class<?>, Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 获取bean实例
     */
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("can not find bean by class:" + cls);
        }
      return (T)BEAN_MAP.get(cls);
    }


}
