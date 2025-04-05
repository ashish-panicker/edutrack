package com.example.enrollment_service.clients;

import com.example.enrollment_service.dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "studentClient", url = "http://localhost:8091/students")
public interface StudentService {

    @GetMapping("/{id}")
    ResponseDto getStudentById(@PathVariable Long id);
}
