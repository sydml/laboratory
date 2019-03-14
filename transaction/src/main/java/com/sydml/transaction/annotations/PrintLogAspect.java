package com.sydml.transaction.annotations;

import com.sydml.common.utils.JsonUtil;
import com.sydml.transaction.domain.User1;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author Liuym
 * @date 2019/3/14 0014
 */
@Component
@Aspect
public class PrintLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintLogAspect.class);
    private static final String SPLIT = ":";
    private static final String BLANK = " ";
    private static final String POINT = ".";


    @Pointcut("@annotation(com.sydml.transaction.annotations.PrintLog)")
    public void controllerAspect() {

    }

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        StringBuilder logContent = new StringBuilder();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();
        Object target = joinPoint.getTarget();
        String className = target.getClass().getSimpleName();
        Parameter[] parameters = method.getParameters();
        logContent.append(className + POINT + method.getName() + POINT + "param.is" + SPLIT);
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getType().isPrimitive() || isWrapClass(parameters[i].getType())) {
                logContent.append("\"" + parameters[i].getName() + "\"" + SPLIT + "\"" + args[i] + "\"" + ",");
            } else {
                logContent.append(JsonUtil.encodeJson(args[i]));
            }
        }
        String content = logContent.toString();
        logProcessor(PrintLog.REQUEST, method, content);
    }


    @AfterReturning(returning = "result", value = "controllerAspect()")
    public void doAfter(JoinPoint joinPoint, Object result) {
        StringBuilder logContent = new StringBuilder();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object target = joinPoint.getTarget();
        String className = target.getClass().getSimpleName();
        String methodName = method.getName();
        logContent.append(className + POINT + methodName + POINT + "result.is" + SPLIT);
        if (result != null) {
            logContent.append(JsonUtil.encodeJson(result));
        }
        String content = logContent.toString();
        logProcessor(PrintLog.RESPONSE, method, content);

    }

    private void logProcessor(String type, Method method, String content) {
        if (!method.isAnnotationPresent(PrintLog.class)) {
            return;
        }
        PrintLog printLog = method.getAnnotation(PrintLog.class);
        String needType = printLog.type();
        String level = printLog.level();
        if (type.equals(needType) || needType.equals(PrintLog.OR)) {
            switch (level) {
                case PrintLog.INFO:
                    LOGGER.info(content);
                    break;
                case PrintLog.WARN:
                    LOGGER.warn(content);
                    break;
                case PrintLog.DEBUG:
                    LOGGER.warn(content);
                    break;
                case PrintLog.ERROR:
                    LOGGER.error(content);
                    break;
                default:
                    return;
            }
        }
    }


    public static boolean isWrapClass(Class clz) {
        try {
            return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(int.class.isPrimitive());
        System.out.println(isWrapClass(Integer.class));
        System.out.println(isWrapClass(Boolean.class));
        System.out.println(isWrapClass(User1.class));
    }
}
