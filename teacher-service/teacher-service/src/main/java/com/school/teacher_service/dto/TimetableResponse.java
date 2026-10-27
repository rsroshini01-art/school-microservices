package com.school.teacher_service.dto;

import com.school.teacher_service.entity.Timetable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimetableResponse {

    private Long id;
    private Long classId;
    private String className;     // filled in Step 6 (from academic-service)
    private Long sectionId;
    private String sectionName;   // filled in Step 6 (from academic-service)
    private Long subjectId;
    private String subjectName;   // filled in Step 6 (from academic-service)
    private Long teacherId;
    private String teacherName;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public static TimetableResponse fromEntity(Timetable t) {
        return new TimetableResponse(
                t.getId(),
                t.getClassId(),
                null,
                t.getSectionId(),
                null,
                t.getSubjectId(),
                null,
                t.getTeacher() != null ? t.getTeacher().getId() : null,
                t.getTeacher() != null ? t.getTeacher().getFirstName() + " " + t.getTeacher().getLastName() : null,
                t.getDayOfWeek(),
                t.getStartTime(),
                t.getEndTime()
        );
    }
}