package com.delicious;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.delicious.mapper")
public class WorkflowMgmtApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkflowMgmtApplication.class, args);
    }
}
