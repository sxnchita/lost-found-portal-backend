package com.springboot.web.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.web.dto.AuditLogResponseDto;
import com.springboot.web.entity.AuditLog;
import com.springboot.web.entity.User;
import com.springboot.web.exception.ResourceNotFoundException;
import com.springboot.web.repository.AuditLogRepository;
import com.springboot.web.repository.UserRepository;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private UserRepository userRepository;

    public void createAuditLog(Long userId, String action, String entityName, Long entityId) {

        User user = null;

        if (userId != null) {
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        }

        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction(action);
        auditLog.setEntityName(entityName);
        auditLog.setEntityId(entityId);

        auditLogRepository.save(auditLog);
    }

    public List<AuditLogResponseDto> getAllAuditLogs() {
        return auditLogRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    private AuditLogResponseDto convertToResponseDto(AuditLog auditLog) {

        AuditLogResponseDto dto = new AuditLogResponseDto();

        dto.setAuditId(auditLog.getAuditId());

        if (auditLog.getUser() != null) {
            dto.setUserId(auditLog.getUser().getUserId());
        }

        dto.setAction(auditLog.getAction());
        dto.setEntityName(auditLog.getEntityName());
        dto.setEntityId(auditLog.getEntityId());
        dto.setCreatedAt(auditLog.getCreatedAt());

        return dto;
    }
}
