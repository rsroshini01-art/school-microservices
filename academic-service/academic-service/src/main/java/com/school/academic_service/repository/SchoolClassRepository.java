package com.school.academic_service.repository;

import com.school.academic_service.entity.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
    List<SchoolClass> findByAcademicYearId(Long academicYearId);
}