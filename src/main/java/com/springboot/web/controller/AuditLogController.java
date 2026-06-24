package com.springboot.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.springboot.web.dto.AuditLogResponseDto;
import com.springboot.web.service.AuditLogService;

@RestController
@RequestMapping("/audit-logs")
@CrossOrigin(origins = "http://localhost:5173")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @GetMapping
    public List<AuditLogResponseDto> getAllAuditLogs() {
        return auditLogService.getAllAuditLogs();
    }

    @GetMapping("/recent")
    public List<AuditLogResponseDto> getRecentAuditLogs() {
        return auditLogService.getRecentAuditLogs();
    }
}