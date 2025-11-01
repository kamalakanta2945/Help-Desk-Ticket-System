package com.example.consumer;

import com.example.dto.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TicketEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(TicketEventConsumer.class);

    @KafkaListener(topics = "ticket-events", groupId = "notification-group")
    public void consume(Ticket ticket) {
        logger.info("Received ticket event: {}", ticket);
        // Add logic to send notifications and update analytics
    }
}
