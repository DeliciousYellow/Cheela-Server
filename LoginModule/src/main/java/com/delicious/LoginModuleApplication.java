package com.delicious;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients//开启openfeign
public class LoginModuleApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoginModuleApplication.class, args);
    }

}
