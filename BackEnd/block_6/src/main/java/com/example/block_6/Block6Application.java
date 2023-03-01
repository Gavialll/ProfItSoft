package com.example.block_6;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Rest API block_5",
        version = "1.0"
))
@Slf4j
public class Block6Application {
    public static void main(String[] args) {
        SpringApplication.run(Block6Application.class, args);
        log.info("Swagger: http://localhost:8080/swagger-ui/index.html");
    }
}
