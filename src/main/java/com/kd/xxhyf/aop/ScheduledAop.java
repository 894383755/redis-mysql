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

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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
public class ScheduledAop {

    private List<String> scheduledEnable;

    private Map<String,List<String>> scheduledEnableMap = new HashMap<>();

    /**
     * 解析scheduledEnable文件为map文件
     */
    @PostConstruct
    public void parsingScheduledEnable(){
        if (scheduledEnable == null){
            return;
        }
        //scheduledEnable.stream().map(e->scheduledEnableMap.put(e,e))
    }

    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void scheduledAspect() {
    }

    @Pointcut("@annotation(com.kd.xxhyf.annotation.EnableAspectAnnotation)")
    public void myAnnotationAspect() {
    }

    @Around("scheduledAspect() || myAnnotationAspect()")
    public Object doInvoke(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName().toLowerCase();
        String funName = joinPoint.getSignature().getName();
        if (!"run".equalsIgnoreCase(funName)||scheduledEnable.contains(className)){
            joinPoint.proceed();
        }
        return null;
    }



}
