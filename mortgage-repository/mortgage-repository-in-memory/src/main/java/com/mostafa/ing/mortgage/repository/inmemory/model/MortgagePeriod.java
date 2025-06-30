package com.mostafa.ing.mortgage.repository.inmemory.model;

import java.time.Period;

/**
 * @param years  of the mortgage period
 * @param months of the mortgage period
 */
public record MortgagePeriod(int years, int months) {
    public static MortgagePeriod of(Period period) {
        return new MortgagePeriod(period.getYears(), period.getMonths());
    }

    public Period toPeriod() {
        return Period.of(years, months, 0);
    }
}
