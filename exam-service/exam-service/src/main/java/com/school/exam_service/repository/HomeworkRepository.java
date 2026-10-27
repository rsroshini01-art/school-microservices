package com.school.exam_service.repository;

import com.school.exam_service.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {
    List<Homework> findByClassIdAndSectionId(Long classId, Long sectionId);
}