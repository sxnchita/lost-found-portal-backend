package com.springboot.web.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.web.entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findTop10ByOrderByCreatedAtDesc();
}