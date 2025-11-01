package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void sendEmail(String to, String subject, String body) {
        logger.info("Sending email to: {}", to);
        logger.info("Subject: {}", subject);
        logger.info("Body: {}", body);
        // In a real application, you would integrate with an email sending library here
    }
}
