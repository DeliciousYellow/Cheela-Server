package com.delicious;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@MapperScan("com.delicious.mapper")
public class NoticeMgmtApplication {
    public static void main(String[] args) {
        SpringApplication.run(NoticeMgmtApplication.class, args);
    }
}
