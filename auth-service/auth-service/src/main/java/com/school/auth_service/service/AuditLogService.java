package com.school.auth_service.service;

import com.school.auth_service.dto.AuditLogResponse;
import com.school.auth_service.entity.AuditLog;
import com.school.auth_service.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    // Reusable method other services will call to log an action
    public void log(String username, String action, String module, String recordId) {
        AuditLog auditLog = new AuditLog();
        auditLog.setUsername(username);
        auditLog.setAction(action);
        auditLog.setModule(module);
        auditLog.setRecordId(recordId);
        auditLogRepository.save(auditLog);
    }

    public Page<AuditLogResponse> getAll(Pageable pageable) {
        return auditLogRepository.findAll(pageable).map(AuditLogResponse::fromEntity);
    }

    public AuditLogResponse getById(Long id) {
        AuditLog log = auditLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Audit log not found with id: " + id));
        return AuditLogResponse.fromEntity(log);
    }
}