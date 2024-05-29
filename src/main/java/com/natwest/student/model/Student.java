package com.natwest.student.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Student {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long studentId;

    private String rollNumber;
    private String studentName; // Changed from firstName and lastName to studentName
    private int science;
    private int maths;
    private int computer;
    private int english;
    private String eligible;

    public Student(String rollNumber, String studentName, int science, int maths, int computer, int english, String eligible) {
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.science = science;
        this.maths = maths;
        this.computer = computer;
        this.english = english;
        this.eligible = eligible;
    }
}
