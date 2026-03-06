package com.notifyflow.ratelimiter.repository;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class RateLimitRepository {

    private final StringRedisTemplate redisTemplate;

    public RateLimitRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long increment(String key) {

        Long count = redisTemplate.opsForValue().increment(key);

        if (count == 1) {
            redisTemplate.expire(key, Duration.ofSeconds(60));
        }

        return count;
    }
}