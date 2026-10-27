package com.school.exam_service.dto;

import com.school.exam_service.entity.Marks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarksResponse {

    private Long id;
    private Long examId;
    private String examName;
    private Long studentId;
    private String studentName;   // filled in Step 6 (from student-service)
    private Long subjectId;
    private String subjectName;   // filled in Step 6 (from academic-service)
    private Double marksObtained;
    private Double maxMarks;
    private Double percentage;
    private String grade;
    private String remarks;

    public static MarksResponse fromEntity(Marks m) {
        double percentage = (m.getMarksObtained() / m.getMaxMarks()) * 100;
        return new MarksResponse(
                m.getId(),
                m.getExam().getId(),
                m.getExam().getExamName(),
                m.getStudentId(),
                null,
                m.getSubjectId(),
                null,
                m.getMarksObtained(),
                m.getMaxMarks(),
                Math.round(percentage * 100.0) / 100.0,
                m.getGrade(),
                m.getRemarks()
        );
    }
}