package com.mostafa.ing.mortgage.model;

public record MortgageCheckResult(boolean feasible, Amount monthlyPayment,
                                  ValidationCode code, String message) {
}
