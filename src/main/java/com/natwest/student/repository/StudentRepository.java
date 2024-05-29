package com.natwest.student.repository;

import com.natwest.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,String> {
    Optional<Student> findByRollNumber(String rollNumber);
}
