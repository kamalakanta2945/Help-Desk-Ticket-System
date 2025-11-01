package com.example.consumer;

import com.example.dto.Ticket;
import com.example.service.AnalyticsService;
import com.example.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TicketEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(TicketEventConsumer.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AnalyticsService analyticsService;

    @KafkaListener(topics = "ticket-events", groupId = "notification-group")
    public void consume(Ticket ticket) {
        logger.info("Received ticket event: {}", ticket);
        notificationService.sendEmail("user@example.com", "Ticket " + ticket.getId() + " created", "Your ticket has been created.");
        analyticsService.updateDashboardMetrics("tickets_created");
    }
}
