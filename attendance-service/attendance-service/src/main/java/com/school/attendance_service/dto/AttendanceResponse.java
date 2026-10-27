package com.school.attendance_service.dto;

import com.school.attendance_service.entity.Attendance;
import com.school.attendance_service.entity.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponse {

    private Long id;
    private Long studentId;
    private String studentName;   // filled in Step 6 (from student-service)
    private LocalDate attendanceDate;
    private AttendanceStatus status;
    private String remarks;

    public static AttendanceResponse fromEntity(Attendance a) {
        return new AttendanceResponse(
                a.getId(),
                a.getStudentId(),
                null,
                a.getAttendanceDate(),
                a.getStatus(),
                a.getRemarks()
        );
    }
}