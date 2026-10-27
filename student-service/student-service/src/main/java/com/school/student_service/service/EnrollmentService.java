package com.school.student_service.service;

import com.school.student_service.dto.EnrollmentRequest;
import com.school.student_service.dto.EnrollmentResponse;
import com.school.student_service.entity.Enrollment;
import com.school.student_service.entity.Student;
import com.school.student_service.repository.EnrollmentRepository;
import com.school.student_service.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    // Academic year, class and section live in academic-service: only ids are stored.
    // Checking that those ids exist will be added in Step 6 (OpenFeign).

    public EnrollmentResponse createEnrollment(EnrollmentRequest request) {
        // Business rule: only one enrollment per student per academic year
        if (enrollmentRepository.findByStudentIdAndAcademicYearId(request.getStudentId(), request.getAcademicYearId()).isPresent()) {
            throw new RuntimeException("Student is already enrolled for this academic year");
        }

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + request.getStudentId()));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setAcademicYearId(request.getAcademicYearId());
        enrollment.setClassId(request.getClassId());
        enrollment.setSectionId(request.getSectionId());
        enrollment.setEnrollmentDate(request.getEnrollmentDate());
        enrollment.setActive(true);

        Enrollment saved = enrollmentRepository.save(enrollment);
        return EnrollmentResponse.fromEntity(saved);
    }

    public List<EnrollmentResponse> getByStudent(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId)
                .stream().map(EnrollmentResponse::fromEntity).collect(Collectors.toList());
    }

    public EnrollmentResponse getById(Long id) {
        Enrollment e = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with id: " + id));
        return EnrollmentResponse.fromEntity(e);
    }

    public EnrollmentResponse updateEnrollment(Long id, EnrollmentRequest request) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with id: " + id));

        enrollment.setClassId(request.getClassId());
        enrollment.setSectionId(request.getSectionId());
        enrollment.setEnrollmentDate(request.getEnrollmentDate());

        Enrollment updated = enrollmentRepository.save(enrollment);
        return EnrollmentResponse.fromEntity(updated);
    }
}