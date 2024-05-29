package com.natwest.student.controller;

import com.natwest.student.dto.EligibilityCriteria;
import com.natwest.student.model.Student;
import com.natwest.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    // Constructor injection of StudentService
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Endpoint to upload and process a file
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            studentService.uploadAndProcessFile(file);
            return ResponseEntity.ok("File uploaded and processed successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file: " + e.getMessage());
        }
    }

    // Endpoint to set eligibility criteria
    @PostMapping("/setEligibilityCriteria")
    public ResponseEntity<String> setEligibilityCriteria(@RequestBody EligibilityCriteria criteria) {
        studentService.setEligibilityCriteria(criteria);
        return ResponseEntity.ok("Eligibility criteria updated successfully");
    }

    // Endpoint to get student by roll number
    @GetMapping("/{rollNumber}")
    public ResponseEntity<Student> getStudent(@PathVariable String rollNumber) {
        Student student = studentService.getStudentByRollNumber(rollNumber);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
