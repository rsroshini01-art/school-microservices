package com.school.exam_service.repository;

import com.school.exam_service.entity.Marks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarksRepository extends JpaRepository<Marks, Long> {

    List<Marks> findByStudentIdAndExamId(Long studentId, Long examId);

    List<Marks> findByExamId(Long examId);

    Optional<Marks> findByExamIdAndStudentIdAndSubjectId(Long examId, Long studentId, Long subjectId);
}