package com.mostafa.ing.mortgage.repository.inmemory.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * @param maturityPeriod maturity period in years
 * @param interestRate   mortgage rate as a percentage
 * @param lastUpdate     the last update timestamp of the rate
 */
public record MortgageRateEntity(int maturityPeriod, BigDecimal interestRate,
                                 OffsetDateTime lastUpdate) {
}
