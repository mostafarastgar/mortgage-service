package com.mostafa.ing.mortgage.model;

import java.math.BigDecimal;

/**
 * @param currency currency of the amount. ATM, currently only Euro (EUR) is supported.
 * @param value    the value of the amount. It must be a positive number.
 */
public record Amount(Currency currency, BigDecimal value) {
    public Amount {
        if (value == null || value.signum() < 0) {
            throw new IllegalArgumentException("Amount value must be a positive number.");
        }
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null.");
        }
    }
}
