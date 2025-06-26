package com.mostafa.ing.mortgage.core.service.validator;

import com.mostafa.ing.mortgage.core.model.ValidationResult;
import com.mostafa.ing.mortgage.model.MortgageCheck;

import java.math.BigDecimal;

/**
 * validates mortgage applications based on the provided mortgage check details.
 */
public interface MortgageValidatorService {
    /**
     * Validates the mortgage application based on the provided mortgage check details.
     *
     * @param mortgageCheck the mortgage check details containing income, home value,
     *                      loan value.
     * @return ValidationResult indicating whether the mortgage application is valid or
     * not.
     */
    ValidationResult validateMortgageApplication(MortgageCheck mortgageCheck);

    /**
     * @param mortgageCheck contains the details of the mortgage application.
     * @param monthlyPayment the calculated monthly payment for the mortgage.
     * @return ValidationResult indicating whether the monthly payment is affordable.
     */
    ValidationResult validateMonthlyPayment(MortgageCheck mortgageCheck, BigDecimal monthlyPayment);
}
