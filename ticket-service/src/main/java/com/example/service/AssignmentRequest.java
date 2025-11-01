package com.example.service;

public class AssignmentRequest {
    private Long departmentId;

    public AssignmentRequest(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
