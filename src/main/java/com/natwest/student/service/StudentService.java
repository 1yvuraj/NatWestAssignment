package com.natwest.student.service;

import com.natwest.student.model.Student;
import com.natwest.student.repository.StudentRepository;
import com.natwest.student.dto.EligibilityCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    private static final String CSV_FILE_PATH = "/Users/yuvrajaggarwal/Desktop/student/src/main/resources/data/Student.csv";
    private EligibilityCriteria eligibilityCriteria = new EligibilityCriteria();

    // Setter method for eligibility criteria
    public void setEligibilityCriteria(EligibilityCriteria criteria) {
        this.eligibilityCriteria = criteria;
        // Update eligibility criteria for all students in the database
        updateEligibilityForAllStudents();
    }

    // Method to upload and process CSV file
    public void uploadAndProcessFile(MultipartFile file) throws Exception {
        List<Student> studentsToUpdateCsv = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            ExecutorService executorService = Executors.newFixedThreadPool(10);

            // Synchronize access to the shared list
            synchronized (studentsToUpdateCsv) {
                reader.lines().skip(1).forEach(line -> {
                    executorService.submit(() -> {
                        try {
                            String[] data = line.split(",");
                            if (data.length == 7) {
                                String rollNumber = data[0].trim();
                                String studentName = data[1].trim();
                                int science = Integer.parseInt(data[2].trim());
                                int maths = Integer.parseInt(data[3].trim());
                                int english = Integer.parseInt(data[4].trim());
                                int computer = Integer.parseInt(data[5].trim());
                                String eligible = data[6].trim();

                                Student student = new Student(rollNumber, studentName, science, maths, computer, english, eligible);
                                student.setEligible(checkEligibility(student));

                                // Add student to the shared list
                                studentsToUpdateCsv.add(student);
                                studentRepository.save(student);
                            } else {
                                System.err.println("Invalid data format: " + line);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                });
            }

            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.SECONDS);

            // Update CSV file after all threads are completed
            updateCsvFile(studentsToUpdateCsv);
        }
    }

    // Method to update eligibility criteria for all students in the database
    @Transactional
    public void updateEligibilityForAllStudents() {
        List<Student> students = studentRepository.findAll();
        for (Student student : students) {
            student.setEligible(checkEligibility(student));
            studentRepository.save(student);
        }
    }

    // Method to update CSV file with student data
    private void updateCsvFile(List<Student> students) {
        try (FileWriter writer = new FileWriter(CSV_FILE_PATH)) {
            writer.append("Roll Number,Student Name,Science,Maths,English,Computer,Eligible\n");
            for (Student student : students) {
                writer.append(String.join(",", Arrays.asList(
                        student.getRollNumber(),
                        student.getStudentName(),
                        String.valueOf(student.getScience()),
                        String.valueOf(student.getMaths()),
                        String.valueOf(student.getEnglish()),
                        String.valueOf(student.getComputer()),
                        student.getEligible()
                ))).append("\n");
            }
            System.out.println("CSV file updated with student data.");
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    // Method to check eligibility of a student based on criteria
    private String checkEligibility(Student student) {
        return (student.getScience() > eligibilityCriteria.getScienceThreshold() &&
                student.getMaths() > eligibilityCriteria.getMathsThreshold() &&
                student.getComputer() > eligibilityCriteria.getComputerThreshold() &&
                student.getEnglish() > eligibilityCriteria.getEnglishThreshold()) ? "YES" : "NO";
    }

    // Method to get student by roll number from the database
    public Student getStudentByRollNumber(String rollNumber) {
        return studentRepository.findByRollNumber(rollNumber).orElse(null);
    }
}
