package com.school.teacher_service.dto;

import com.school.teacher_service.entity.TeacherClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherClassResponse {

    private Long id;
    private Long teacherId;
    private String teacherName;
    private Long classId;
    private String className;   // filled in Step 6 (from academic-service)

    public static TeacherClassResponse fromEntity(TeacherClass tc) {
        return new TeacherClassResponse(
                tc.getId(),
                tc.getTeacher().getId(),
                tc.getTeacher().getFirstName() + " " + tc.getTeacher().getLastName(),
                tc.getClassId(),
                null
        );
    }
}