package com.school.exam_service.repository;

import com.school.exam_service.entity.ExamSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamSubjectRepository extends JpaRepository<ExamSubject, Long> {

    List<ExamSubject> findByExamId(Long examId);

    Optional<ExamSubject> findByExamIdAndSubjectId(Long examId, Long subjectId);
}