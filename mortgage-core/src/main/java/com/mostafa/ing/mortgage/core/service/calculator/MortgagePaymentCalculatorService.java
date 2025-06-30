package com.mostafa.ing.mortgage.core.service.calculator;

import com.mostafa.ing.mortgage.model.MortgageCheck;
import com.mostafa.ing.mortgage.model.MortgageRate;

import java.math.BigDecimal;

/**
 * Calculates the monthly mortgage payment based on the principal amount, annual interest rate, and term in years.
 */
public interface MortgagePaymentCalculatorService {
    /**
     * @param mortgageCheck contains the details of the mortgage application details.
     * @param mortgageRate contains the interest rate details for the mortgage.
     * @return the monthly payment amount as a BigDecimal.
     */
    BigDecimal calculateMonthlyPayment(MortgageCheck mortgageCheck, MortgageRate mortgageRate);
}
