package com.mostafa.ing.mortgage.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.Period;

/**
 * @param maturityPeriod maturity period of the mortgage rate
 * @param interestRate interest rate of the mortgage
 * @param lastUpdate last update time of the mortgage rate
 */
public record MortgageRate(Period maturityPeriod, BigDecimal interestRate,
                           OffsetDateTime lastUpdate) {
}
