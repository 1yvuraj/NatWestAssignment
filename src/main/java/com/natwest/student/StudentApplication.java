package com.natwest.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication // Indicates that this is the main Spring Boot application class
@EntityScan(basePackages = "com.natwest.student.model") // Scans for JPA entities in the specified package
@EnableJpaRepositories(basePackages = "com.natwest.student.repository") // Enables JPA repositories in the specified package
public class StudentApplication {

    public static void main(String[] args) {
        // Method to bootstrap and launch the Spring application
        SpringApplication.run(StudentApplication.class, args);
    }

}
