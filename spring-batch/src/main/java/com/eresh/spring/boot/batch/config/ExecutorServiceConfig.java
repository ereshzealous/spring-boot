package com.eresh.spring.boot.batch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author Gorantla, Eresh
 * @created 07-02-2019
 */
@Configuration
public class ExecutorServiceConfig {
    @Bean("fixedThreadPool")
    public ExecutorService fixedThreadPool() {
        return Executors.newFixedThreadPool(5);
    }

    @Bean("databasePersistThreadPool")
    public ExecutorService databasePersistThreadPool() {
        ExecutorService executorService = new ThreadPoolExecutor(5, 15, 2, TimeUnit.MINUTES, new ArrayBlockingQueue<>(100));
        return executorService;
    }
}
