package com.school.auth_service.dto;

import com.school.auth_service.entity.AuditLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponse {

    private Long id;
    private String username;
    private String action;
    private String module;
    private String recordId;
    private LocalDateTime timestamp;

    public static AuditLogResponse fromEntity(AuditLog log) {
        return new AuditLogResponse(log.getId(), log.getUsername(), log.getAction(), log.getModule(), log.getRecordId(), log.getTimestamp());
    }
}