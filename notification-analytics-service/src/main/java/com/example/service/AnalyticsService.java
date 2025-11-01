package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {

    private static final Logger logger = LoggerFactory.getLogger(AnalyticsService.class);

    public void updateDashboardMetrics(String metric) {
        logger.info("Updating dashboard metric: {}", metric);
        // In a real application, you would update a database or another analytics service here
    }
}
