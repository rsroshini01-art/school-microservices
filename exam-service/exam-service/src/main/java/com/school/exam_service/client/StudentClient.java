package com.school.exam_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "student-service")
public interface StudentClient {

    @GetMapping("/api/students/{id}")
    Map<String, Object> getStudent(@PathVariable("id") Long id);

    @GetMapping("/api/students")
    Map<String, Object> getStudentsByClass(@RequestParam("classId") Long classId,
                                           @RequestParam("limit") int limit);
}