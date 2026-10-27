package com.school.teacher_service.repository;

import com.school.teacher_service.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    boolean existsByEmployeeId(String employeeId);

    @Query("SELECT t FROM Teacher t WHERE " +
            "(:search IS NULL OR LOWER(t.firstName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(t.lastName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(t.employeeId) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Teacher> searchTeachers(@Param("search") String search, Pageable pageable);
}