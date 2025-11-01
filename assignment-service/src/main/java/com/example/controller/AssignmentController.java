package com.example.controller;

import com.example.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("/assign")
    public ResponseEntity<?> assignTicket(@RequestBody AssignmentRequest request) {
        try {
            Long agentId = assignmentService.assignTicket(request.getDepartmentId());
            return ResponseEntity.ok(Map.of("assignedAgentId", agentId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
