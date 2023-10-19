package com.delicious.component;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


/**
 * 基于redis实现的分布式锁
 */
@Component
public class RedisLock {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public boolean acquireLock(String lockKey, long expireTimeInSeconds) {
        Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, "locked");
        if (locked != null && locked) {
            // 设置锁的过期时间
            // 三个参数分别是，锁的主键，过期时间，该时间的单位
            redisTemplate.expire(lockKey, expireTimeInSeconds, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    public void releaseLock(String lockKey) {
        redisTemplate.delete(lockKey);
    }
}