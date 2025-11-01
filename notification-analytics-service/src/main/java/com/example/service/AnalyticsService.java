package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {

    private static final Logger logger = LoggerFactory.getLogger(AnalyticsService.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void updateDashboardMetrics(String metric) {
        logger.info("Updating dashboard metric: {}", metric);
        redisTemplate.opsForValue().increment("analytics:" + metric);
    }
}
