package com.mostafa.ing.mortgage.exception;

import com.mostafa.ing.mortgage.model.ValidationCode;
import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
    private final ValidationCode code;

    public ValidationException(String message, ValidationCode code) {
        super(message);
        this.code = code;
    }
}
