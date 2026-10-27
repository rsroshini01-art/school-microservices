package com.school.exam_service.repository;

import com.school.exam_service.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByAcademicYearId(Long academicYearId);
}