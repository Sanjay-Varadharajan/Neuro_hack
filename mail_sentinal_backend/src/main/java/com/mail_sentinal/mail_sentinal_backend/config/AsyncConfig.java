package com.mail_sentinal.mail_sentinal_backend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {

    @Bean(name = "smsExecutor")
    public Executor smsExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);   // number of threads to run in parallel
        executor.setMaxPoolSize(20);    // maximum threads
        executor.setQueueCapacity(100); // queue for pending tasks
        executor.setThreadNamePrefix("SMS-");
        executor.initialize();
        return executor;
    }
}
