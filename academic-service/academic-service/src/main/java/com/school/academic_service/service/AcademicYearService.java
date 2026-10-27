package com.school.academic_service.service;

import com.school.academic_service.dto.AcademicYearRequest;
import com.school.academic_service.dto.AcademicYearResponse;
import com.school.academic_service.entity.AcademicYear;
import com.school.academic_service.entity.School;
import com.school.academic_service.repository.AcademicYearRepository;
import com.school.academic_service.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcademicYearService {

    @Autowired
    private AcademicYearRepository academicYearRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    public AcademicYearResponse createAcademicYear(AcademicYearRequest request) {
        if (!request.getStartDate().isBefore(request.getEndDate())) {
            throw new RuntimeException("Start date must be before end date");
        }

        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new RuntimeException("School not found with id: " + request.getSchoolId()));

        AcademicYear year = new AcademicYear();
        year.setSchool(school);
        year.setYearName(request.getYearName());
        year.setStartDate(request.getStartDate());
        year.setEndDate(request.getEndDate());
        year.setActive(false); // must be explicitly activated

        AcademicYear saved = academicYearRepository.save(year);
        return AcademicYearResponse.fromEntity(saved);
    }

    public List<AcademicYearResponse> getAcademicYears(Long schoolId) {
        List<AcademicYear> years = academicYearRepository.findBySchoolId(schoolId);
        return years.stream().map(AcademicYearResponse::fromEntity).collect(Collectors.toList());
    }

    public AcademicYearResponse getAcademicYearById(Long id) {
        AcademicYear year = academicYearRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Academic year not found with id: " + id));
        return AcademicYearResponse.fromEntity(year);
    }

    // Business rule: only ONE active academic year per school
    public AcademicYearResponse activateAcademicYear(Long id) {
        AcademicYear year = academicYearRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Academic year not found with id: " + id));

        // deactivate the currently active year for this school (if any)
        academicYearRepository.findBySchoolIdAndActiveTrue(year.getSchool().getId())
                .ifPresent(currentActive -> {
                    currentActive.setActive(false);
                    academicYearRepository.save(currentActive);
                });

        year.setActive(true);
        AcademicYear saved = academicYearRepository.save(year);
        return AcademicYearResponse.fromEntity(saved);
    }

    public void deleteAcademicYear(Long id) {
        AcademicYear year = academicYearRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Academic year not found with id: " + id));
        if (year.isActive()) {
            throw new RuntimeException("Cannot delete an active academic year");
        }
        academicYearRepository.deleteById(id);
    }
}