package com.school.communication_service.dto;

import com.school.communication_service.entity.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponse {

    private Long id;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private Long uploadedById;
    private String uploadedByEmail;   // filled in Step 6 (from auth-service)
    private LocalDateTime uploadedAt;

    public static DocumentResponse fromEntity(Document d) {
        return new DocumentResponse(
                d.getId(),
                d.getFileName(),
                d.getFileType(),
                d.getFileSize(),
                d.getUploadedById(),
                null,
                d.getUploadedAt()
        );
    }
}