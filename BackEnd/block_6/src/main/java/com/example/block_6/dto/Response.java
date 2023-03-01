package com.example.block_6.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

public class Response {
    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Status <T> {
        private HttpStatus status;
        private String message;
        private T body;

        public Status(HttpStatus status, String message) {
            this.status = status;
            this.message = message;
        }
    }

    private Response(){};
}
