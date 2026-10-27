package com.school.academic_service.dto;

import com.school.academic_service.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResponse {

    private Long id;
    private String subjectName;
    private String subjectCode;
    private Long classId;
    private String className;
    private Long teacherId;
    private String teacherName;   // filled in Step 6 (from teacher-service)

    public static SubjectResponse fromEntity(Subject s) {
        return new SubjectResponse(
                s.getId(),
                s.getSubjectName(),
                s.getSubjectCode(),
                s.getSchoolClass().getId(),
                s.getSchoolClass().getClassName(),
                s.getTeacherId(),
                null
        );
    }
}