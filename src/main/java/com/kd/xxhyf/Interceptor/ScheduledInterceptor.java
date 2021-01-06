package com.kd.xxhyf.Interceptor;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拦截计划任务
 */
@Slf4j
@Component
@Aspect
@Data
@ConfigurationProperties(prefix = "config")
public class ScheduledInterceptor {

    private List<String> scheduledEnable;

    private Map<String,List<String>> scheduledEnableMap = new HashMap<>();


    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void scheduledAspect() {
    }

    @Pointcut("@annotation(com.kd.xxhyf.annotation.EnableAspectAnnotation)")
    public void myAnnotationAspect() {
    }

    @Around("scheduledAspect() || myAnnotationAspect()")
    public Object doInvoke(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName().toLowerCase();
        String methodName = joinPoint.getSignature().getName();
        String fullName = className + "." + methodName;
        if (scheduledEnable.contains(fullName)){
            joinPoint.proceed();
        }
        return null;
    }



}
