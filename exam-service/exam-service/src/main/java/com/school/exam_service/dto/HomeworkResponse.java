package com.school.exam_service.dto;

import com.school.exam_service.entity.Homework;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkResponse {

    private Long id;
    private Long classId;
    private String className;     // filled in Step 6 (from academic-service)
    private Long sectionId;
    private String sectionName;   // filled in Step 6 (from academic-service)
    private Long subjectId;
    private String subjectName;   // filled in Step 6 (from academic-service)
    private Long teacherId;
    private String teacherName;   // filled in Step 6 (from teacher-service)
    private String title;
    private String description;
    private LocalDate dueDate;

    public static HomeworkResponse fromEntity(Homework h) {
        return new HomeworkResponse(
                h.getId(),
                h.getClassId(),
                null,
                h.getSectionId(),
                null,
                h.getSubjectId(),
                null,
                h.getTeacherId(),
                null,
                h.getTitle(),
                h.getDescription(),
                h.getDueDate()
        );
    }
}