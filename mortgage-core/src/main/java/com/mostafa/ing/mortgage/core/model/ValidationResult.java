package com.mostafa.ing.mortgage.core.model;

import com.mostafa.ing.mortgage.model.ValidationCode;

/**
 * @param valid   is the validation result valid or not
 * @param validationCode is the validationCode indicating the type of validation result
 * @param message is a descriptive message providing details about the validation result
 */
public record ValidationResult(boolean valid, ValidationCode validationCode, String message) {
}
