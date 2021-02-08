package com.kd.xxhyf.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

@Slf4j
@Configuration
public class MyAsyncConfigurer implements AsyncConfigurer {


    @Value("${thread.corePoolSize}")
    private int corePoolSize;
    @Value("${thread.maxPoolSize}")
    private int maxPoolSize;
    @Value("${thread.queueCapacity}")
    private int queueCapacity;
    @Value("${thread.keepAliveSeconds}")
    private int keepAliveSeconds;
    @Value("${thread.rejectedExecutionHandler}")
    private String rejectedExecutionHandler;

    private static ThreadPoolTaskExecutor executor;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(corePoolSize);
        threadPool.setMaxPoolSize(maxPoolSize);
        threadPool.setQueueCapacity(queueCapacity);
        threadPool.setKeepAliveSeconds(keepAliveSeconds);
        threadPool.setWaitForTasksToCompleteOnShutdown(true);
        threadPool.setAwaitTerminationSeconds(60 * 15);
        threadPool.setThreadNamePrefix("MyAsync-");
        threadPool.initialize();
        executor = threadPool;
        return threadPool;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new MyAsyncExceptionHandler();
    }

    public static int getActiveCount() {
        return executor.getActiveCount();
    }

    /**
     * 自定义异常处理类
     *
     */
    class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
            log.error("Method name - " + method.getName());
            for (Object object : objects) {
                log.error("Parameter value - " + object);
            }
            log.error("Exception message - " + throwable.getMessage());
        }
    }
}
