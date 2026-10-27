package com.school.exam_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "academic-service")
public interface AcademicClient {

    @GetMapping("/api/subjects/{id}")
    Map<String, Object> getSubject(@PathVariable("id") Long id);
}