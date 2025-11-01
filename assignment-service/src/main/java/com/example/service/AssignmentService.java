package com.example.service;

import com.example.domain.Agent;
import com.example.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Long assignTicket(Long departmentId) {
        List<Agent> agents = agentRepository.findByDepartmentId(departmentId);
        if (agents.isEmpty()) {
            throw new RuntimeException("No agents found for department: " + departmentId);
        }

        // Find the agent with the minimum ticket load
        Optional<Agent> selectedAgent = agents.stream()
                .min(Comparator.comparingInt(agent -> getAgentLoadFromCache(agent.getId())));

        if (selectedAgent.isPresent()) {
            Agent agent = selectedAgent.get();
            incrementAgentLoadInCache(agent.getId());
            return agent.getId();
        } else {
            throw new RuntimeException("Could not select an agent.");
        }
    }

    private int getAgentLoadFromCache(Long agentId) {
        Integer load = (Integer) redisTemplate.opsForValue().get("agent_load:" + agentId);
        return load != null ? load : 0;
    }

    private void incrementAgentLoadInCache(Long agentId) {
        redisTemplate.opsForValue().increment("agent_load:" + agentId);
    }
}
