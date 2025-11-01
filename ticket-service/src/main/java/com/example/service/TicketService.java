package com.example.service;

import com.example.domain.Ticket;
import com.example.domain.TicketStatus;
import com.example.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private RestTemplate restTemplate;

    private static final String TICKET_TOPIC = "ticket-events";

    public Ticket createTicket(Ticket ticket) {
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());

        // Assign the ticket
        AssignmentRequest request = new AssignmentRequest(ticket.getDepartment().getId());
        Map<String, Long> response = restTemplate.postForObject("http://assignment-service/api/assignments/assign", request, Map.class);
        if (response != null && response.containsKey("assignedAgentId")) {
            ticket.setAssignedToId(response.get("assignedAgentId"));
        }

        Ticket savedTicket = ticketRepository.save(ticket);
        kafkaTemplate.send(TICKET_TOPIC, "created", savedTicket);
        return savedTicket;
    }

    public Optional<Ticket> getTicketById(Long id) {
        return ticketRepository.findById(id);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket updateTicket(Long id, Ticket ticketDetails) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));

        ticket.setTitle(ticketDetails.getTitle());
        ticket.setDescription(ticketDetails.getDescription());
        ticket.setStatus(ticketDetails.getStatus());
        ticket.setDepartment(ticketDetails.getDepartment());
        ticket.setCategory(ticketDetails.getCategory());
        ticket.setAssignedToId(ticketDetails.getAssignedToId());
        ticket.setUpdatedAt(LocalDateTime.now());

        Ticket updatedTicket = ticketRepository.save(ticket);
        kafkaTemplate.send(TICKET_TOPIC, "updated", updatedTicket);
        return updatedTicket;
    }
}
