package com.school.attendance_service.service;

import com.school.attendance_service.client.StudentClient;
import com.school.attendance_service.dto.AttendanceRequest;
import com.school.attendance_service.dto.AttendanceResponse;
import com.school.attendance_service.entity.Attendance;
import com.school.attendance_service.repository.AttendanceRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentClient studentClient;

    // Asks student-service for the student. Throws an error if the student does not exist.
    @SuppressWarnings("unchecked")
    private String fetchStudentName(Long studentId) {
        try {
            Map<String, Object> body = studentClient.getStudent(studentId);
            Map<String, Object> data = (Map<String, Object>) body.get("data");
            return data.get("firstName") + " " + data.get("lastName");
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Student not found with id: " + studentId);
        }
    }

    public AttendanceResponse markAttendance(AttendanceRequest request) {
        if (attendanceRepository.findByStudentIdAndAttendanceDate(request.getStudentId(), request.getAttendanceDate()).isPresent()) {
            throw new RuntimeException("Attendance already marked for this student on " + request.getAttendanceDate());
        }

        String studentName = fetchStudentName(request.getStudentId());

        Attendance attendance = new Attendance();
        attendance.setStudentId(request.getStudentId());
        attendance.setAttendanceDate(request.getAttendanceDate());
        attendance.setStatus(request.getStatus());
        attendance.setRemarks(request.getRemarks());

        Attendance saved = attendanceRepository.save(attendance);
        AttendanceResponse response = AttendanceResponse.fromEntity(saved);
        response.setStudentName(studentName);
        return response;
    }

    public AttendanceResponse updateAttendance(Long id, AttendanceRequest request) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance record not found with id: " + id));
        attendance.setStatus(request.getStatus());
        attendance.setRemarks(request.getRemarks());
        Attendance updated = attendanceRepository.save(attendance);
        return AttendanceResponse.fromEntity(updated);
    }

    public List<AttendanceResponse> getByStudent(Long studentId) {
        String studentName = fetchStudentName(studentId);
        return attendanceRepository.findByStudentId(studentId)
                .stream().map(AttendanceResponse::fromEntity)
                .peek(r -> r.setStudentName(studentName))
                .collect(Collectors.toList());
    }

    public List<AttendanceResponse> getByDate(LocalDate date) {
        return attendanceRepository.findByAttendanceDate(date)
                .stream().map(AttendanceResponse::fromEntity).collect(Collectors.toList());
    }

    public void deleteAttendance(Long id) {
        if (!attendanceRepository.existsById(id)) {
            throw new RuntimeException("Attendance record not found with id: " + id);
        }
        attendanceRepository.deleteById(id);
    }
}