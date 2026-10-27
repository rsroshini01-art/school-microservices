package com.school.exam_service.repository;

import com.school.exam_service.entity.HomeworkSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HomeworkSubmissionRepository extends JpaRepository<HomeworkSubmission, Long> {

    Optional<HomeworkSubmission> findByHomeworkIdAndStudentId(Long homeworkId, Long studentId);

    List<HomeworkSubmission> findByHomeworkId(Long homeworkId);

    List<HomeworkSubmission> findByStudentId(Long studentId);
}