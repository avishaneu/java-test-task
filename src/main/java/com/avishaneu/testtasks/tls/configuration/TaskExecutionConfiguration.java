package com.avishaneu.testtasks.tls.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by avishaneu on 7/25/17.
 */
@Configuration
public class TaskExecutionConfiguration {

    @Value("${taskexecutor.pool.core}")
    private int corePoolSize;

    @Value("${taskexecutor.pool.max}")
    private int maxPoolSize;

    @Value("${taskexecutor.queue.capacity}")
    private int queueCapacity;

    @Bean
    @Qualifier("taskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        return threadPoolTaskExecutor;
    }
}
