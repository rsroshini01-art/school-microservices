package com.school.academic_service.repository;

import com.school.academic_service.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findBySchoolClassId(Long classId);
}