package com.edutrack.student_service.controller;

import com.edutrack.student_service.dto.RequestDto;
import com.edutrack.student_service.dto.ResponseDto;
import com.edutrack.student_service.model.Student;
import com.edutrack.student_service.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService service) {
        this.studentService = service;
    }


    @PostMapping
    public ResponseEntity<ResponseDto> createCourse(@Valid @RequestBody RequestDto dto) {
        var student = Student.builder()
                .email(dto.getEmail())
                .enrolledCourse(dto.getEnrolledCourse())
                .name(dto.getName())
                .build();
        student = studentService.save(student);
        dto.setId(student.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseDto.builder()
                        .status(HttpStatus.CREATED)
                        .payload(dto).build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getCourseById(@PathVariable Long id) {
        return studentService.findById(id)
                .map(student -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(
                                ResponseDto.builder().status(HttpStatus.CREATED).payload(student).build()
                        )
                )
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getAllCourses() {
        var students = studentService.findAll();
        if (students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    ResponseDto.builder().status(HttpStatus.NO_CONTENT).payload(List.of()).build()
            );
        }
        return ResponseEntity.ok(ResponseDto.builder().status(HttpStatus.NO_CONTENT).payload(students).build());
    }
}
