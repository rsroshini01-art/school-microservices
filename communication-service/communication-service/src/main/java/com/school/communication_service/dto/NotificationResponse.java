package com.school.communication_service.dto;

import com.school.communication_service.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private Long id;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;

    public static NotificationResponse fromEntity(Notification n) {
        return new NotificationResponse(n.getId(), n.getMessage(), n.isRead(), n.getCreatedAt());
    }
}