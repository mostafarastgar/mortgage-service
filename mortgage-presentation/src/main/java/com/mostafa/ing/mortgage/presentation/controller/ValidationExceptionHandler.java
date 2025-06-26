package com.mostafa.ing.mortgage.presentation.controller;

import com.mostafa.ing.mortgage.exception.ValidationException;
import com.mostafa.ing.mortgage.presentation.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ValidationExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDto> handleValidationException(ValidationException ex) {
        log.error("Validation error occurred with code: {} and message: {}",
                ex.getCode().getValue(), ex.getMessage(), ex);
        ErrorDto response = new ErrorDto(ex.getCode().getValue(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}