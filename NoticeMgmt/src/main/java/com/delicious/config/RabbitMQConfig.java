package com.delicious.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
//    @Bean
//    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
//        return factory;
//    }
    //rabbitmq三步：
    //1.定义交换机
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("exchange.fanout.notice");
    }
    //2.定义队列
    @Bean
    public Queue queueNotice(){
        return new Queue("queue.fanout.notice.8085");
    }
    //3.绑定交换机和队列
    @Bean
    public Binding bindingNotice(FanoutExchange fanoutExchange, Queue queueNotice){
        //把队列queueNotice绑定到扇形交换机
        return BindingBuilder.bind(queueNotice).to(fanoutExchange);
    }
}
