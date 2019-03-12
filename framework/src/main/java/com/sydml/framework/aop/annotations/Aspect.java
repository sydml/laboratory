package com.sydml.framework.aop.annotations;

import java.lang.annotation.*;

/**
 * Created by Yuming-Liu
 * 日期： 2019-03-12
 * 时间： 22:54
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();
}
