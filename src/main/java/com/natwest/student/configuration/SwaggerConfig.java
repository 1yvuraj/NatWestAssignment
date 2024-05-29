package com.natwest.student.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    // Configuring Swagger Docket bean
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // Specifying base package for API controllers to scan
                .apis(RequestHandlerSelectors.basePackage("com.example"))
                // Defining API paths to include in documentation
                .paths(PathSelectors.any())
                .build();
    }
}
