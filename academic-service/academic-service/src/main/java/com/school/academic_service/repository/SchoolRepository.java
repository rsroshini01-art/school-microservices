package com.school.academic_service.repository;

import com.school.academic_service.entity.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SchoolRepository extends JpaRepository<School, Long> {

    @Query("SELECT s FROM School s WHERE " +
            "(:search IS NULL OR LOWER(s.schoolName) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<School> searchSchools(@Param("search") String search, Pageable pageable);
}