package com.school.academic_service.service;

import com.school.academic_service.dto.SchoolClassRequest;
import com.school.academic_service.dto.SchoolClassResponse;
import com.school.academic_service.entity.AcademicYear;
import com.school.academic_service.entity.SchoolClass;
import com.school.academic_service.repository.AcademicYearRepository;
import com.school.academic_service.repository.SchoolClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolClassService {

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private AcademicYearRepository academicYearRepository;

    public SchoolClassResponse createClass(SchoolClassRequest request) {
        AcademicYear year = academicYearRepository.findById(request.getAcademicYearId())
                .orElseThrow(() -> new RuntimeException("Academic year not found with id: " + request.getAcademicYearId()));

        SchoolClass sc = new SchoolClass();
        sc.setAcademicYear(year);
        sc.setClassName(request.getClassName());
        sc.setDescription(request.getDescription());
        sc.setStatus(true);

        SchoolClass saved = schoolClassRepository.save(sc);
        return SchoolClassResponse.fromEntity(saved);
    }

    public List<SchoolClassResponse> getClasses(Long academicYearId) {
        return schoolClassRepository.findByAcademicYearId(academicYearId)
                .stream().map(SchoolClassResponse::fromEntity).collect(Collectors.toList());
    }

    public SchoolClassResponse getClassById(Long id) {
        SchoolClass sc = schoolClassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + id));
        return SchoolClassResponse.fromEntity(sc);
    }

    public SchoolClassResponse updateClass(Long id, SchoolClassRequest request) {
        SchoolClass sc = schoolClassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + id));
        sc.setClassName(request.getClassName());
        sc.setDescription(request.getDescription());
        SchoolClass updated = schoolClassRepository.save(sc);
        return SchoolClassResponse.fromEntity(updated);
    }

    public void deleteClass(Long id) {
        if (!schoolClassRepository.existsById(id)) {
            throw new RuntimeException("Class not found with id: " + id);
        }
        schoolClassRepository.deleteById(id);
    }
}