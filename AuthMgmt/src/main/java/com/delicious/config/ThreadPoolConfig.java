package com.delicious.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPoolConfig {

    // 创建一个固定大小的线程池，包含3个线程，并指定线程名称前缀
    @Bean
    public ExecutorService createThreadPool() {
        return Executors.newFixedThreadPool(3);
    }
}