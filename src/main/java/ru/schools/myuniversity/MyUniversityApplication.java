package ru.schools.myuniversity;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class MyUniversityApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyUniversityApplication.class, args);
    }

}
