package com.sydml.framework.ioc.ioc;

import com.sydml.common.utils.ArrayUtil;
import com.sydml.common.utils.CollectionUtil;
import com.sydml.common.utils.ReflectionUtil;
import com.sydml.framework.ioc.annotation.Autowired;
import com.sydml.framework.ioc.handler.BeanHandler;
import com.sydml.framework.ioc.handler.ClassHandler;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * IOC 容器
 *
 * @author Liuym
 * @date 2019/3/10 0010
 */
public final class IocCore {
    static {
        //获取说有的bean类和bean实例的映射关系
        Map<Class<?>, Object> beanMap = BeanHandler.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    for (Field beanField : beanFields) {
                        if (beanField.isAnnotationPresent(Autowired.class)) {
                            //此处的Autowired注解必须使用类型是实现类，如果是按照接口注入的则此处无法处理
                            Class<?> beanFieldClass = beanField.getType();
                            // 支持接口多实现
                            if (beanFieldClass.isInterface()) {
                                Class<?> implementClass = ClassHandler.getImplementClass(beanField, beanFieldClass);
                                Object beanFieldInstance = beanMap.get(implementClass);
                                if (beanFieldInstance != null) {
                                    //DI 反射进行依赖注入
                                    ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                                }
                            } else {
                                // 非接口实现类直接注入
                                Object beanFieldInstance = beanMap.get(beanFieldClass);
                                if (beanFieldInstance != null) {
                                    //DI 反射进行依赖注入
                                    ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        System.out.println();
    }
}
