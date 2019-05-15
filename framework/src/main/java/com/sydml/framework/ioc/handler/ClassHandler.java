package com.sydml.framework.ioc.handler;

import com.sydml.common.utils.StringUtil;
import com.sydml.framework.ioc.annotation.*;
import com.sydml.framework.ioc.utils.ClassUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Liuym hand
 * @date 2019/3/10 0010
 */
public final class ClassHandler {
    public static Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHandler.getBasePackages();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    public static Set<Class<?>> getClassSet() {
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
     */
    public static Set<Class<?>> getComponentClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Component.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取所有的bean类
     *
     * @return
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getControllerClassSet());
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getComponentClassSet());
        return beanClassSet;
    }

    /**
     * 接口实现类有多个的处理
     *
     * @param beanField
     * @param beanFieldClass
     * @return
     */
    public static Class<?> getImplementClass(Field beanField, Class<?> beanFieldClass) {

        //所有实现类
        List<Class<?>> implementClassList =
                ClassHandler.getClassSet().stream().filter(it -> beanFieldClass.isAssignableFrom(it) && !beanFieldClass.equals(it)).collect(Collectors.toList());
        //如果只有一个实现类,返回实现类
        if (implementClassList.size() == 1) {
            return implementClassList.get(0);
        }
        // 实现类和类名小写的映射
//        Map<Class<?>, String> implementClassNameMap = implementClassList.stream().distinct().collect(Collectors.toMap(Function.identity
//        (), it -> it.getSimpleName().toLowerCase()));
        Map<String, Class<?>> implementClassNameMap =
                implementClassList.stream().distinct().collect(Collectors.toMap(it -> it.getSimpleName().toLowerCase(),
                        Function.identity()));

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

    /**
     * 获取应用包下的某父类(接口)的说有子类(或者实现类)
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包下带有某注解的所有类
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {

            if (cls.isAnnotationPresent(annotationClass)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

}
