package com.gjq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 项目启动类
 */
@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class FireMonitoringApplication {
    public static void main(String[] args) {
        SpringApplication.run(FireMonitoringApplication.class, args);
    }
} 