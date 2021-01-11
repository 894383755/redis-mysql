package com.kd.xxhyf.config;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;


@Configurable
public class MyTaskExecutorConfig {

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

    private static ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Bean
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setQueueCapacity(queueCapacity);
        taskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        if("CallerRunsPolicy".equalsIgnoreCase(rejectedExecutionHandler)){
            taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        }
        threadPoolTaskExecutor = taskExecutor;
        return taskExecutor;
    }

    public static void shutdown(){
        threadPoolTaskExecutor.shutdown();
    }
}
