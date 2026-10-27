package com.school.academic_service.repository;

import com.school.academic_service.entity.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long> {

    List<AcademicYear> findBySchoolId(Long schoolId);

    Optional<AcademicYear> findBySchoolIdAndActiveTrue(Long schoolId);
}