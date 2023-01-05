package com.example.block_5.exeption;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class globalExceptionHandler {
        @ExceptionHandler(IllegalArgumentException.class)
        protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e
                    .getBindingResult()
                    .getFieldError()
                    .getDefaultMessage());
        }
        @ExceptionHandler(ConstraintViolationException.class)
        protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e
                    .getMessage());
        }

        @ExceptionHandler(NoSuchElementException.class)
        protected ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException e) {
            return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }

        private static ResponseEntity<Object> buildErrorResponse(HttpStatus httpStatus, String message) {
            ErrorResponse response = new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase(), message);
            return ResponseEntity.status(httpStatus.value()).body(response);
        }

        @Getter
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        static class ErrorResponse {
            private int status;
            private String error;
            private String message;
        }
}
