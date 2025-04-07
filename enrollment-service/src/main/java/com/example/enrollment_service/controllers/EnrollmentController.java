package com.example.enrollment_service.controllers;

import com.example.enrollment_service.dto.RequestDto;
import com.example.enrollment_service.dto.ResponseDto;
import com.example.enrollment_service.model.Enrollment;
import com.example.enrollment_service.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> enrollStudent(@Valid @RequestBody RequestDto dto) {
        var enrollment = Enrollment.builder().studentId(dto.getStudentId()).courseId(dto.getCourseId()).build();
        var savedEnrollment = service.save(enrollment);
        var response = ResponseDto.builder().status(HttpStatus.CREATED).response(savedEnrollment).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> findEnrollment(@PathVariable Long id) {
        var enrollment = service.findById(id).orElseThrow();
        var response = ResponseDto.builder().status(HttpStatus.OK).response(enrollment).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<ResponseDto> findAllEnrollments() {
        var enrollments = service.findAll();
        if (enrollments.isEmpty()) {
            var response = ResponseDto.builder().status(HttpStatus.NO_CONTENT).response(List.of()).build();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
        var response = ResponseDto.builder().status(HttpStatus.OK).response(enrollments).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // GET /details/{id}
    @GetMapping("/{id}/details")
    public ResponseEntity<ResponseDto> findEnrollmentDetails(@PathVariable Long id){
        var enrollment = service.findById(id).orElseThrow();
        var studentId = enrollment.getStudentId();
        var courseId = enrollment.getCourseId();

        var studentDetails =  service.findStudentDetails(studentId);
        var courseDetails = service.findCourseDetails(courseId);

        HashMap<String, Object> payload = new HashMap<>();
        payload.put("enrollment", enrollment);
        payload.put("student", studentDetails);
        payload.put("course", courseDetails);

        var response = ResponseDto.builder().status(HttpStatus.OK).response(payload).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
