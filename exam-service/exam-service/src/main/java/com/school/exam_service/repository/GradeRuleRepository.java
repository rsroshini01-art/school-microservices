package com.school.exam_service.repository;

import com.school.exam_service.entity.GradeRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GradeRuleRepository extends JpaRepository<GradeRule, Long> {

    List<GradeRule> findAllByOrderByMinPercentageDesc();

    Optional<GradeRule> findFirstByMinPercentageLessThanEqualAndMaxPercentageGreaterThanEqual(Double percentage1, Double percentage2);
}