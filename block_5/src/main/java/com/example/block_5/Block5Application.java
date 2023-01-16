package com.example.block_5;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Rest API block_5",
        version = "1.0"
))
public class Block5Application {
    public static void main(String[] args) {
        SpringApplication.run(Block5Application.class, args);
    }

}
