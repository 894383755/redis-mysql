package com.kd.xxhyf.aop;

import com.kd.xxhyf.annotation.EnableAspectAnnotation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 拦截计划任务
 */
@Slf4j
@Component
@Aspect
@Data
@ConfigurationProperties(prefix = "config")
public class ScheduledAop {

    private List<String> scheduledEnable;


    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void scheduledAspect() {
    }

    @Around("scheduledAspect()")
    public Object doInvoke(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName().toLowerCase();
        String funName = joinPoint.getSignature().getName();
        if (!"run".equalsIgnoreCase(funName)||scheduledEnable.contains(className)){
            joinPoint.proceed();
        }
        return null;
    }

}
