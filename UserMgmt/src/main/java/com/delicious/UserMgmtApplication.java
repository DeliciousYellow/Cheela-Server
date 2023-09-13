package com.delicious;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.delicious.mapper")
public class UserMgmtApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserMgmtApplication.class, args);
    }

}
