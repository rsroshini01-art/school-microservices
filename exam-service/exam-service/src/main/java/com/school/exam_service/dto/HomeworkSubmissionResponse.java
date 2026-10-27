package com.school.exam_service.dto;

import com.school.exam_service.entity.HomeworkSubmission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkSubmissionResponse {

    private Long id;
    private Long homeworkId;
    private String homeworkTitle;
    private Long studentId;
    private String studentName;   // filled in Step 6 (from student-service)
    private String submissionText;
    private String attachmentUrl;
    private LocalDateTime submittedAt;
    private Double marksAwarded;
    private String feedback;
    private String status;

    public static HomeworkSubmissionResponse fromEntity(HomeworkSubmission s) {
        return new HomeworkSubmissionResponse(
                s.getId(),
                s.getHomework().getId(),
                s.getHomework().getTitle(),
                s.getStudentId(),
                null,
                s.getSubmissionText(),
                s.getAttachmentUrl(),
                s.getSubmittedAt(),
                s.getMarksAwarded(),
                s.getFeedback(),
                s.getStatus()
        );
    }
}