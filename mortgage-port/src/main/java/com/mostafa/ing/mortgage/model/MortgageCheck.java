package com.mostafa.ing.mortgage.model;

import java.time.Period;

/**
 * @param income annual income of the applicant
 * @param maturityPeriod the maturity period of the mortgage
 * @param loanValue the value of the loan requested
 * @param homeValue the value of the home
 */
public record MortgageCheck(Amount income, Period maturityPeriod,
                            Amount loanValue, Amount homeValue) {
}
