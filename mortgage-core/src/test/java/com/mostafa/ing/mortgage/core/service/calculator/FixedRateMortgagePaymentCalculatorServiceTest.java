package com.mostafa.ing.mortgage.core.service.calculator;

import com.mostafa.ing.mortgage.model.Amount;
import com.mostafa.ing.mortgage.model.Currency;
import com.mostafa.ing.mortgage.model.MortgageCheck;
import com.mostafa.ing.mortgage.model.MortgageRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;

class FixedRateMortgagePaymentCalculatorServiceTest {

    private FixedRateMortgagePaymentCalculatorService subject;

    private MortgageCheck mortgageCheck;
    private MortgageRate mortgageRate;

    @BeforeEach
    void setUp() {
        subject = new FixedRateMortgagePaymentCalculatorService();
        mortgageCheck = new MortgageCheck(
                new Amount(Currency.EUR, new BigDecimal("60000")),
                Period.ofYears(30),
                new Amount(Currency.EUR, new BigDecimal("240000")),
                new Amount(Currency.EUR, new BigDecimal("260000"))
        );
        mortgageRate = new MortgageRate(Period.ofYears(30), new BigDecimal("4.5"), null);
    }

    @Test
    void testCalculateMonthlyPayment_ValidInputs() {
        BigDecimal monthlyPayment = subject.calculateMonthlyPayment(mortgageCheck, mortgageRate);

        assertNotNull(monthlyPayment);
        assertEquals(new BigDecimal("1216.0447440000"), monthlyPayment); // Expected value based on formula
    }

    @Test
    void testCalculateMonthlyPayment_ZeroInterestRate() {
        mortgageRate = new MortgageRate(Period.ofYears(30), BigDecimal.ZERO, null);

        BigDecimal monthlyPayment = subject.calculateMonthlyPayment(mortgageCheck, mortgageRate);

        assertNotNull(monthlyPayment);
        assertEquals(new BigDecimal("666.6666666667"), monthlyPayment); // Principal divided by months
    }

    @Test
    void testCalculateMonthlyPayment_ZeroLoanValue() {
        mortgageCheck = new MortgageCheck(
                new Amount(Currency.EUR, new BigDecimal("60000")),
                Period.ofYears(30),
                new Amount(Currency.EUR, BigDecimal.ZERO),
                new Amount(Currency.EUR, new BigDecimal("60000"))
        );

        BigDecimal monthlyPayment = subject.calculateMonthlyPayment(mortgageCheck, mortgageRate);

        assertNotNull(monthlyPayment);
        assertEquals(BigDecimal.ZERO, monthlyPayment);
    }

    @Test
    void testRateOverYearsCache() {
        double firstCalculation = subject.calculateMonthlyPayment(mortgageCheck, mortgageRate).doubleValue();
        double cachedCalculation = subject.calculateMonthlyPayment(mortgageCheck, mortgageRate).doubleValue();

        assertEquals(firstCalculation, cachedCalculation);
    }
}