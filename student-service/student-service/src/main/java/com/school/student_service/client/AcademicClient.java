package com.school.student_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "academic-service")
public interface AcademicClient {

    @GetMapping("/api/classes/{id}")
    Map<String, Object> getSchoolClass(@PathVariable("id") Long id);
}