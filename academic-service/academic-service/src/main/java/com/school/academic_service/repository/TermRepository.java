package com.school.academic_service.repository;

import com.school.academic_service.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TermRepository extends JpaRepository<Term, Long> {
    List<Term> findByAcademicYearId(Long academicYearId);
}