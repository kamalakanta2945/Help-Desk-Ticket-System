package com.example.service;

import com.example.domain.Agent;
import com.example.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    @Autowired
    private AgentRepository agentRepository;

    public Long assignTicket(Long departmentId) {
        List<Agent> agents = agentRepository.findByDepartmentId(departmentId);
        if (agents.isEmpty()) {
            throw new RuntimeException("No agents found for department: " + departmentId);
        }

        // Find the agent with the minimum ticket load
        Optional<Agent> selectedAgent = agents.stream()
                .min(Comparator.comparingInt(Agent::getTicketLoad));

        if (selectedAgent.isPresent()) {
            Agent agent = selectedAgent.get();
            agent.setTicketLoad(agent.getTicketLoad() + 1);
            agentRepository.save(agent);
            return agent.getId();
        } else {
            throw new RuntimeException("Could not select an agent.");
        }
    }
}
