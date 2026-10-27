package com.school.student_service.repository;

import com.school.student_service.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudentId(Long studentId);

    Optional<Enrollment> findByStudentIdAndAcademicYearId(Long studentId, Long academicYearId);

    List<Enrollment> findByClassIdAndSectionId(Long classId, Long sectionId);
}