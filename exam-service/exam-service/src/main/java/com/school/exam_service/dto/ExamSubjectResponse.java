package com.school.exam_service.dto;

import com.school.exam_service.entity.ExamSubject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamSubjectResponse {

    private Long id;
    private Long examId;
    private String examName;
    private Long subjectId;
    private String subjectName;   // filled in Step 6 (from academic-service)
    private Double maxMarks;

    public static ExamSubjectResponse fromEntity(ExamSubject es) {
        return new ExamSubjectResponse(
                es.getId(),
                es.getExam().getId(),
                es.getExam().getExamName(),
                es.getSubjectId(),
                null,
                es.getMaxMarks()
        );
    }
}