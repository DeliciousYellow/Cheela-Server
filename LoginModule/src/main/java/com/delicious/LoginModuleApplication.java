package com.delicious;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients//开启openfeign
//@LoadBalancerClients
public class LoginModuleApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoginModuleApplication.class, args);
    }

}
