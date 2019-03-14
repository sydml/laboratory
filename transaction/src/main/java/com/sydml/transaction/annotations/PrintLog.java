package com.sydml.transaction.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 控制调用接口时，对输入参数和输出结果的日志打印
 *
 * @author Liuym
 * @date 2019/3/14 0014
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrintLog {

    String INFO = "info";
    String WARN = "warn";
    String ERROR = "error";
    String DEBUG = "debug";

    String REQUEST = "request";

    String RESPONSE = "response";

    String OR = "response & request";

    String level() default INFO;

    String type() default REQUEST;
}
