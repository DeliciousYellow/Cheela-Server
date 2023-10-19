package com.delicious;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
@MapperScan("com.delicious.mapper")
public class WorkflowMgmtApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkflowMgmtApplication.class, args);
    }
}
