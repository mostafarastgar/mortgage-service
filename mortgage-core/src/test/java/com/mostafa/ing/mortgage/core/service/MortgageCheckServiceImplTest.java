package com.mostafa.ing.mortgage.core.service;

import com.mostafa.ing.mortgage.core.model.ValidationResult;
import com.mostafa.ing.mortgage.core.service.calculator.MortgagePaymentCalculatorService;
import com.mostafa.ing.mortgage.core.service.validator.MortgageValidatorService;
import com.mostafa.ing.mortgage.exception.ValidationException;
import com.mostafa.ing.mortgage.model.Amount;
import com.mostafa.ing.mortgage.model.Currency;
import com.mostafa.ing.mortgage.model.MortgageCheck;
import com.mostafa.ing.mortgage.model.MortgageCheckResult;
import com.mostafa.ing.mortgage.model.MortgageRate;
import com.mostafa.ing.mortgage.model.ValidationCode;
import com.mostafa.ing.mortgage.port.service.MortgageRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Period;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MortgageCheckServiceImplTest {

    @Mock
    private MortgageRateService mortgageRateService;

    @Mock
    private MortgageValidatorService mortgageValidatorService;

    @Mock
    private MortgagePaymentCalculatorService mortgagePaymentCalculatorService;

    @InjectMocks
    private MortgageCheckServiceImpl subject;

    private MortgageCheck mortgageCheck;
    private MortgageRate mortgageRate;

    @BeforeEach
    void setUp() {
        mortgageCheck = new MortgageCheck(new Amount(Currency.EUR, new BigDecimal("100000")),
                Period.ofYears(30),
                new Amount(Currency.EUR, new BigDecimal("200000")),
                new Amount(Currency.EUR, new BigDecimal("300000")));
        mortgageRate = new MortgageRate(Period.ofYears(30), new BigDecimal("3.5"), null);
    }

    @Test
    void testCheckMortgage_ValidApplication() {
        ValidationResult validationResult = new ValidationResult(true, null, null);
        BigDecimal monthlyPayment = new BigDecimal("1216.04");

        when(mortgageValidatorService.validateMortgageApplication(mortgageCheck)).thenReturn(validationResult);
        when(mortgageRateService.findByPeriod(mortgageCheck.maturityPeriod())).thenReturn(Optional.of(mortgageRate));
        when(mortgagePaymentCalculatorService.calculateMonthlyPayment(mortgageCheck, mortgageRate)).thenReturn(monthlyPayment);
        when(mortgageValidatorService.validateMonthlyPayment(mortgageCheck, monthlyPayment)).thenReturn(validationResult);

        MortgageCheckResult result = subject.checkMortgage(mortgageCheck);

        assertTrue(result.feasible());
        assertEquals(new Amount(Currency.EUR, monthlyPayment), result.monthlyPayment());
        assertNull(result.code());
        assertNull(result.message());
    }

    @Test
    void testCheckMortgage_BusinessValidationError() {
        ValidationResult validationResult = new ValidationResult(false, ValidationCode.CODE_INVALID_BASED_ON_HOME_VALUE,
                "Loan value cannot be greater than home value.");

        when(mortgageValidatorService.validateMortgageApplication(mortgageCheck)).thenReturn(validationResult);

        MortgageCheckResult result = subject.checkMortgage(mortgageCheck);

        assertFalse(result.feasible());
        assertNull(result.monthlyPayment());
        assertEquals(ValidationCode.CODE_INVALID_BASED_ON_HOME_VALUE, result.code());
        assertEquals("Loan value cannot be greater than home value.", result.message());
    }

    @Test
    void testCheckMortgage_CoreValidationError() {
        ValidationResult validationResult = new ValidationResult(false, ValidationCode.CODE_INVALID_INCOME,
                "Income must be greater than zero.");

        when(mortgageValidatorService.validateMortgageApplication(mortgageCheck)).thenReturn(validationResult);

        ValidationException exception = assertThrows(ValidationException.class, () -> subject.checkMortgage(mortgageCheck));

        assertEquals("Income must be greater than zero.", exception.getMessage());
        assertEquals(ValidationCode.CODE_INVALID_INCOME, exception.getCode());
    }

    @Test
    void testCheckMortgage_NoMortgageRateFound() {
        ValidationResult validationResult = new ValidationResult(true, null, null);

        when(mortgageValidatorService.validateMortgageApplication(mortgageCheck)).thenReturn(validationResult);
        when(mortgageRateService.findByPeriod(mortgageCheck.maturityPeriod())).thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class, () -> subject.checkMortgage(mortgageCheck));

        assertEquals("No mortgage rate found for the specified maturity period: P30Y", exception.getMessage());
        assertEquals(ValidationCode.CODE_INVALID_MATURITY_PERIOD, exception.getCode());
    }
}