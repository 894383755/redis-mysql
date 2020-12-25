package com.kd.xxhyf.config;

import com.kd.xxhyf.main.Run;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

@Component
public class MyAsyncConfigurer implements AsyncConfigurer {
    private static final Logger LOGGER =  LoggerFactory.getLogger(MyAsyncConfigurer.class);

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(1);
        threadPool.setMaxPoolSize(1);
        threadPool.setWaitForTasksToCompleteOnShutdown(true);
        threadPool.setAwaitTerminationSeconds(60 * 15);
        threadPool.setThreadNamePrefix("MyAsync-");
        threadPool.initialize();
        return threadPool;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new MyAsyncExceptionHandler();
    }

    /**
     * 自定义异常处理类
     *
     */
    class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {


        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
            LOGGER.error("Method name - " + method.getName());
            for (Object object : objects) {
                LOGGER.error("Parameter value - " + object);
            }
            LOGGER.error("Exception message - " + throwable.getMessage());
        }
    }
}