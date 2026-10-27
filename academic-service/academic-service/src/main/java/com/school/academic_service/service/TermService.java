package com.school.academic_service.service;

import com.school.academic_service.dto.TermRequest;
import com.school.academic_service.dto.TermResponse;
import com.school.academic_service.entity.AcademicYear;
import com.school.academic_service.entity.Term;
import com.school.academic_service.repository.AcademicYearRepository;
import com.school.academic_service.repository.TermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TermService {

    @Autowired
    private TermRepository termRepository;

    @Autowired
    private AcademicYearRepository academicYearRepository;

    public TermResponse createTerm(TermRequest request) {
        AcademicYear year = academicYearRepository.findById(request.getAcademicYearId())
                .orElseThrow(() -> new RuntimeException("Academic year not found with id: " + request.getAcademicYearId()));

        Term term = new Term();
        term.setAcademicYear(year);
        term.setTermName(request.getTermName());
        term.setStartDate(request.getStartDate());
        term.setEndDate(request.getEndDate());

        Term saved = termRepository.save(term);
        return TermResponse.fromEntity(saved);
    }

    public List<TermResponse> getTerms(Long academicYearId) {
        return termRepository.findByAcademicYearId(academicYearId)
                .stream().map(TermResponse::fromEntity).collect(Collectors.toList());
    }

    public TermResponse getTermById(Long id) {
        Term term = termRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Term not found with id: " + id));
        return TermResponse.fromEntity(term);
    }

    public TermResponse updateTerm(Long id, TermRequest request) {
        Term term = termRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Term not found with id: " + id));
        term.setTermName(request.getTermName());
        term.setStartDate(request.getStartDate());
        term.setEndDate(request.getEndDate());
        Term updated = termRepository.save(term);
        return TermResponse.fromEntity(updated);
    }

    public void deleteTerm(Long id) {
        if (!termRepository.existsById(id)) {
            throw new RuntimeException("Term not found with id: " + id);
        }
        termRepository.deleteById(id);
    }
}