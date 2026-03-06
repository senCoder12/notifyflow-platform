package com.notifyflow.ratelimiter.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.notifyflow.ratelimiter.repository.RateLimitRepository;

import java.time.Duration;

@Service
public class RateLimiterService {

    private final RateLimitRepository rateLimitRepository;

    private static final int MAX_REQUESTS = 5;
    private static final int WINDOW = 60;

    public RateLimiterService(RateLimitRepository rateLimitRepository) {
        this.rateLimitRepository = rateLimitRepository;
    }

    public boolean isAllowed(String userId) {

        String key = "rate_limit:" + userId;

        Long count = rateLimitRepository.increment(key);

        return count <= MAX_REQUESTS;
    }
}