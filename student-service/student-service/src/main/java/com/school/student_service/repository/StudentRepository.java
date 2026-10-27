package com.school.student_service.repository;

import com.school.student_service.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByAdmissionNumber(String admissionNumber);

    @Query("SELECT s FROM Student s WHERE " +
            "(:search IS NULL OR LOWER(s.firstName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(s.admissionNumber) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:classId IS NULL OR s.classId = :classId) " +
            "AND (:sectionId IS NULL OR s.sectionId = :sectionId) " +
            "AND (:status IS NULL OR s.status = :status)")
    Page<Student> searchStudents(@Param("search") String search,
                                 @Param("classId") Long classId,
                                 @Param("sectionId") Long sectionId,
                                 @Param("status") Boolean status,
                                 Pageable pageable);
}