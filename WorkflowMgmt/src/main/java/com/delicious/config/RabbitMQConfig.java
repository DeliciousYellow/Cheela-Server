package com.delicious.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    // 1. 定义直连交换机
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("exchange.direct.workflow");
    }

    // 2. 定义队列
    @Bean
    public Queue queueNotice() {
        return new Queue("queue.direct.workflow");
    }

    // 3. 绑定交换机和队列，使用指定的路由键
    @Bean
    public Binding bindingNotice(DirectExchange directExchange, Queue queueNotice) {
        // 把队列 queueNotice 绑定到直连交换机，使用指定的路由键 "routing.workflow"
        return BindingBuilder.bind(queueNotice).to(directExchange).with("routing.workflow");
    }
}
