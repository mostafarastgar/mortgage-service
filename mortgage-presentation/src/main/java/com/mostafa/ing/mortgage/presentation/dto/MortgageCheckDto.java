package com.mostafa.ing.mortgage.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

@Schema(description = "DTO representing mortgage rate details")
@Validated
public record MortgageCheckDto(
        @Schema(description = "Maturity period in years")
        @Valid
        @NotNull(message = "Annual income is required.")
        AmountDto annualIncome,
        @Schema(description = "Maturity period in years", example = "30", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Maturity period is required.")
        @Positive(message = "Maturity period must be a positive number.")
        @Max(value = 50, message = "Maturity period cannot exceed 50 years.")
        @Digits(integer = 2, fraction = 0, message = "Maturity period must be a whole number with up to two digits.")
        Integer maturityPeriod,
        @Schema(description = "Loan value")
        @Valid
        @NotNull(message = "Loan value is required.")
        AmountDto loanValue,
        @Schema(description = "Home value")
        @Valid
        @NotNull(message = "Home value is required.")
        AmountDto homeValue) {
}
