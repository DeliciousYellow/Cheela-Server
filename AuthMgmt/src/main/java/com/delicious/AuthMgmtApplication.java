package com.delicious;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.delicious.mapper")
public class AuthMgmtApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthMgmtApplication.class, args);
    }
}