package com.edutrack.student_service.service;

import com.edutrack.student_service.model.Student;
import com.edutrack.student_service.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;
    @Override
    public Student save(Student student) {
        return repository.save(student);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Student> findAll() {
        return repository.findAll();
    }
}
