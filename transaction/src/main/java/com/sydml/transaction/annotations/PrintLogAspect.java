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
    private static final String POINT = ".";
    private static final String MARKS = "\"";
    private static final String COMMA = ",";
    private static final String PARAM_IS = "param.is";
    private static final String RESULT_IS = "result.is";
    private static final String RESULT = "result";
    private static final String TYPE = "TYPE";


    @Pointcut("@annotation(com.sydml.transaction.annotations.PrintLog)")
    public void controllerAspect() {}

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        StringBuilder logContent = new StringBuilder();
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Object[] args = joinPoint.getArgs();
            Object target = joinPoint.getTarget();
            String className = target.getClass().getSimpleName();
            Parameter[] parameters = method.getParameters();
            logContent.append(className + POINT + method.getName() + POINT + PARAM_IS + SPLIT);
            if (args.length == 0) {
                return;
            }
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].getType().isPrimitive() || isWrapClass(parameters[i].getType())) {
                    logContent.append(MARKS + parameters[i].getName() + MARKS + SPLIT + MARKS + args[i] + MARKS + COMMA);
                } else {
                    logContent.append(JsonUtil.encodeJson(args[i]));
                }
            }
            String content = logContent.toString();
            logProcessor(PrintLog.REQUEST, method, content);
        } catch (Exception e) {
            LOGGER.warn("PrintLogAspect.processor.request.log.print.error");
        }
    }


    @AfterReturning(returning = RESULT, value = "controllerAspect()")
    public void doAfter(JoinPoint joinPoint, Object result) {
        if (result == null) {
            return;
        }
        StringBuilder logContent = new StringBuilder();
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Object target = joinPoint.getTarget();
            String className = target.getClass().getSimpleName();
            String methodName = method.getName();
            logContent.append(className + POINT + methodName + POINT + RESULT_IS + SPLIT);
            if (result != null) {
                logContent.append(JsonUtil.encodeJson(result));
            }
            String content = logContent.toString();
            logProcessor(PrintLog.RESPONSE, method, content);
        } catch (Exception e) {
            LOGGER.warn("PrintLogAspect.processor.return.log.print.error");
        }

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
            return ((Class) clz.getField(TYPE).get(null)).isPrimitive();
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
