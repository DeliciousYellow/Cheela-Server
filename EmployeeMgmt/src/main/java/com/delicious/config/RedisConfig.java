package com.delicious.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.delicious.pojo.LoginUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, LoginUserDetails> redisTemplateStringUser(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, LoginUserDetails> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置redis序列化方式
        //这里采用alibaba的FastJson
        redisTemplate.setValueSerializer(new FastJsonRedisSerializer<>(LoginUserDetails.class));
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, Integer> redisTemplateStringInteger(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}