package com.delicious.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

public class DepartConfig {
//    @Bean
//    public ReactorLoadBalancer<ServiceInstance> randomBalancer(Environment e, LoadBalancerClientFactory factory){
//        //获取负载均衡客户端名称。即服务提供者名称
//        String name = e.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
//        return new RandomLoadBalancer(factory.getLazyProvider(name, ServiceInstanceListSupplier.class),name);
//    }
}
