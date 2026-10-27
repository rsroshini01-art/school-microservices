package com.school.exam_service.service;

import com.school.exam_service.dto.GradeRuleRequest;
import com.school.exam_service.dto.GradeRuleResponse;
import com.school.exam_service.entity.GradeRule;
import com.school.exam_service.repository.GradeRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeRuleService {

    @Autowired
    private GradeRuleRepository gradeRuleRepository;

    public GradeRuleResponse createRule(GradeRuleRequest request) {
        if (request.getMinPercentage() > request.getMaxPercentage()) {
            throw new RuntimeException("Min percentage cannot be greater than max percentage");
        }

        GradeRule rule = new GradeRule();
        rule.setMinPercentage(request.getMinPercentage());
        rule.setMaxPercentage(request.getMaxPercentage());
        rule.setGrade(request.getGrade());
        rule.setPassGrade(request.isPassGrade());

        GradeRule saved = gradeRuleRepository.save(rule);
        return GradeRuleResponse.fromEntity(saved);
    }

    public List<GradeRuleResponse> getAllRules() {
        return gradeRuleRepository.findAllByOrderByMinPercentageDesc()
                .stream().map(GradeRuleResponse::fromEntity).collect(Collectors.toList());
    }

    public GradeRuleResponse updateRule(Long id, GradeRuleRequest request) {
        GradeRule rule = gradeRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade rule not found with id: " + id));
        rule.setMinPercentage(request.getMinPercentage());
        rule.setMaxPercentage(request.getMaxPercentage());
        rule.setGrade(request.getGrade());
        rule.setPassGrade(request.isPassGrade());
        GradeRule updated = gradeRuleRepository.save(rule);
        return GradeRuleResponse.fromEntity(updated);
    }

    public void deleteRule(Long id) {
        if (!gradeRuleRepository.existsById(id)) {
            throw new RuntimeException("Grade rule not found with id: " + id);
        }
        gradeRuleRepository.deleteById(id);
    }

    // Used by MarksService to look up the correct grade dynamically
    public String resolveGrade(double percentage) {
        return gradeRuleRepository.findFirstByMinPercentageLessThanEqualAndMaxPercentageGreaterThanEqual(percentage, percentage)
                .map(GradeRule::getGrade)
                .orElse("N/A");
    }

    public boolean isPassing(double percentage) {
        return gradeRuleRepository.findFirstByMinPercentageLessThanEqualAndMaxPercentageGreaterThanEqual(percentage, percentage)
                .map(GradeRule::isPassGrade)
                .orElse(false);
    }
}