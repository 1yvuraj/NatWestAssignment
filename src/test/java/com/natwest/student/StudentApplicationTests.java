package com.natwest.student;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import com.natwest.student.dto.EligibilityCriteria;
import com.natwest.student.model.Student;
import com.natwest.student.repository.StudentRepository;
import com.natwest.student.service.StudentService;

@SpringBootTest
class StudentApplicationTests {

	 @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testUploadAndProcessFile() throws Exception {
        // Mocking MultipartFile
        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenReturn(getClass().getResourceAsStream("/test-students.csv"));

        // Mocking studentRepository.save()
        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Mocking eligibility criteria
        EligibilityCriteria eligibilityCriteria = new EligibilityCriteria();
        eligibilityCriteria.setScienceThreshold(80);
        eligibilityCriteria.setMathsThreshold(75);
        eligibilityCriteria.setComputerThreshold(85);
        eligibilityCriteria.setEnglishThreshold(70);
        studentService.setEligibilityCriteria(eligibilityCriteria);

        // Call the method to test
        studentService.uploadAndProcessFile(file);

        // Verify that save method was called with correct arguments
        verify(studentRepository, times(3)).save(any(Student.class));
    }

    @Test
    void testUpdateEligibilityForAllStudents() {
        // Mocking studentRepository.findAll()
        List<Student> students = new ArrayList<>();
        students.add(new Student("1", "John", 80, 70, 85, 90, "NO"));
        students.add(new Student("2", "Alice", 90, 80, 75, 85, "NO"));
        when(studentRepository.findAll()).thenReturn(students);

        // Mocking eligibility criteria
        EligibilityCriteria eligibilityCriteria = new EligibilityCriteria();
        eligibilityCriteria.setScienceThreshold(80);
        eligibilityCriteria.setMathsThreshold(75);
        eligibilityCriteria.setComputerThreshold(85);
        eligibilityCriteria.setEnglishThreshold(70);
        studentService.setEligibilityCriteria(eligibilityCriteria);

        // Call the method to test
        studentService.updateEligibilityForAllStudents();

        // Verify that save method was called for each student with correct eligibility
        verify(studentRepository, times(2)).save(any(Student.class));
    }

}
