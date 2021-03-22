package com.kd.xxhyf.Interceptor;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
@Data
public class LogInterceptor {
    @Pointcut("@annotation(com.kd.xxhyf.annotation.EnableAspectAnnotation)")
    public void myAnnotationAspect() {
    }

    @Around(" myAnnotationAspect()")
    public Object doInvoke(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName().toLowerCase();
        String methodName = joinPoint.getSignature().getName();
        String fullName = className + "." + methodName;
        log.info("执行开始:"+fullName );
        joinPoint.proceed();
        log.info("执行完成:"+fullName);
        return null;
    }
}
