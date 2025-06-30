package com.mostafa.ing.mortgage.core.service.validator;

import com.mostafa.ing.mortgage.core.model.ValidationResult;
import com.mostafa.ing.mortgage.model.Amount;
import com.mostafa.ing.mortgage.model.Currency;
import com.mostafa.ing.mortgage.model.MortgageCheck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Period;

import static com.mostafa.ing.mortgage.model.ValidationCode.CODE_INVALID_BASED_ON_HOME_VALUE;
import static com.mostafa.ing.mortgage.model.ValidationCode.CODE_TOO_LOW_INCOME;
import static com.mostafa.ing.mortgage.model.ValidationCode.CODE_VALID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BasicMortgageCheckValidatorServiceTest {
    private static final BigDecimal INCOME_MULTIPLICAND = BigDecimal.valueOf(4);

    private BasicMortgageCheckValidatorService subject;

    private MortgageCheck mortgageCheck;

    @BeforeEach
    void setUp() {
        subject = new BasicMortgageCheckValidatorService(INCOME_MULTIPLICAND);
        mortgageCheck = new MortgageCheck(
                new Amount(Currency.EUR, new BigDecimal("60000")),
                Period.ofYears(30),
                new Amount(Currency.EUR, new BigDecimal("240000")),
                new Amount(Currency.EUR, new BigDecimal("260000"))
        );
    }

    @Test
    void testValidateMortgageApplication_ValidApplication() {
        ValidationResult result = subject.validateMortgageApplication(mortgageCheck);

        assertTrue(result.valid());
        assertEquals(CODE_VALID, result.validationCode());
        assertEquals("Mortgage application is valid.", result.message());
    }

    @Test
    void testValidateMortgageApplication_InvalidCurrency() {
        assertThrows(IllegalArgumentException.class, () -> new Amount(null,
                new BigDecimal("100000")), "Currency must not be null");
    }

    @Test
    void testValidateMortgageApplication_NegativeHomeValue() {
        assertThrows(IllegalArgumentException.class, () -> new Amount(Currency.EUR, new BigDecimal("-200000")),
                "Value must be greater than zero");
    }

    @Test
    void testValidateMortgageApplication_LoanValueExceedsHomeValue() {
        mortgageCheck = new MortgageCheck(
                new Amount(Currency.EUR, new BigDecimal("300000")),
                Period.ofYears(30),
                new Amount(Currency.EUR, new BigDecimal("200000")),
                new Amount(Currency.EUR, new BigDecimal("3000"))
        );

        ValidationResult result = subject.validateMortgageApplication(mortgageCheck);

        assertFalse(result.valid());
        assertEquals(CODE_INVALID_BASED_ON_HOME_VALUE, result.validationCode());
        assertEquals("Loan value cannot be greater than home value.", result.message());
    }

    @Test
    void testValidateMonthlyPayment_TooLowIncome() {
        BigDecimal monthlyPayment = new BigDecimal("9000");

        ValidationResult result = subject.validateMonthlyPayment(mortgageCheck, monthlyPayment);

        assertFalse(result.valid());
        assertEquals(CODE_TOO_LOW_INCOME, result.validationCode());
        assertEquals("Monthly payment exceeds the monthly income.", result.message());
    }

    @Test
    void testValidateMonthlyPayment_AffordablePayment() {
        BigDecimal monthlyPayment = new BigDecimal("100");

        ValidationResult result = subject.validateMonthlyPayment(mortgageCheck, monthlyPayment);

        assertTrue(result.valid());
        assertEquals(CODE_VALID, result.validationCode());
        assertEquals("Monthly payment is affordable.", result.message());
    }
}