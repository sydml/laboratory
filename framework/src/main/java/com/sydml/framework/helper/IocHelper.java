package com.sydml.framework.helper;

import com.sydml.common.utils.ArrayUtil;
import com.sydml.common.utils.CollectionUtil;
import com.sydml.common.utils.ReflectionUtil;
import com.sydml.common.utils.StringUtil;
import com.sydml.framework.annotation.Autowired;
import com.sydml.framework.annotation.Order;
import com.sydml.framework.annotation.Primary;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Liuym
 * @date 2019/3/10 0010
 */
public final class IocHelper {
    static {
        //获取说有的bean类和bean实例的映射关系
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
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
                                Class<?> implementClass = getImplementClass(beanField, beanFieldClass);
                                Object beanFieldInstance = beanMap.get(implementClass);
                                if (beanFieldInstance != null) {
                                    //DI 反射进行依赖注入
                                    ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                                }
                            }else{
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

    /**
     * 接口实现类有多个的处理
     * @param beanField
     * @param beanFieldClass
     * @return
     */
    private static Class<?> getImplementClass(Field beanField, Class<?> beanFieldClass) {

        //所有实现类
        List<Class<?>> implementClassList = ClassHelper.getClassSet().stream().filter(it -> it.isAssignableFrom(beanFieldClass)).collect(Collectors.toList());
        //如果只有一个实现类,返回实现类
        if (implementClassList.size() == 2) {
            return implementClassList.get(0).isInterface() ? implementClassList.get(1) : implementClassList.get(0);
        }
        // 实现类和类名小写的映射
//        Map<Class<?>, String> implementClassNameMap = implementClassList.stream().distinct().collect(Collectors.toMap(Function.identity(), it -> it.getSimpleName().toLowerCase()));
        Map<String, Class<?>> implementClassNameMap = implementClassList.stream().distinct().collect(Collectors.toMap(it -> it.getSimpleName().toLowerCase(), Function.identity()));

        // 优先按照字段上Autowired注解写的名字获取，默认为类名首字母小写
        Annotation[] fieldAnnotations = beanField.getDeclaredAnnotations();

        for (Annotation fieldAnnotation : fieldAnnotations) {
            if (fieldAnnotation instanceof Autowired) {
                String autowiredValue = ((Autowired) fieldAnnotation).value();
                if (StringUtil.isNotEmpty(autowiredValue)) {
                   return implementClassNameMap.get(autowiredValue.toLowerCase());
                }
            }
        }
        // 如果实现类有写Primary注解,则按照这个实现类返回，如果实现类写有Order注解 则按照顺序返回排在最前面的，两个都写了，优先按照primary返回
        Map<Integer, Class<?>> orderImplementClassMap = new HashMap<>();
        List<Integer> orderList = new ArrayList<>();
        for (Class<?> implementClass : implementClassList) {
            if (implementClass.isAnnotationPresent(Primary.class)) {
                return implementClass;
            } else if (implementClass.isAnnotationPresent(Order.class)) {
                Order order = implementClass.getAnnotation(Order.class);
                int value = order.value();
                orderImplementClassMap.put(value, implementClass);
                orderList.add(value);
            }
        }
        Integer integer = orderList.stream().min(Integer::compare).get();
        return orderImplementClassMap.get(integer);
    }

    public static void main(String[] args) {
        System.out.println();
        System.out.println();
    }
}
