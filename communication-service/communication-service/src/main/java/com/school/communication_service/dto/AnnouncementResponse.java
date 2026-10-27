package com.school.communication_service.dto;

import com.school.communication_service.entity.Announcement;
import com.school.communication_service.entity.TargetAudience;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementResponse {

    private Long id;
    private String title;
    private String content;
    private TargetAudience targetAudience;
    private Long classId;
    private String className;   // filled in Step 6 (from academic-service)
    private boolean published;
    private LocalDateTime createdAt;

    public static AnnouncementResponse fromEntity(Announcement a) {
        return new AnnouncementResponse(
                a.getId(),
                a.getTitle(),
                a.getContent(),
                a.getTargetAudience(),
                a.getClassId(),
                null,
                a.isPublished(),
                a.getCreatedAt()
        );
    }
}