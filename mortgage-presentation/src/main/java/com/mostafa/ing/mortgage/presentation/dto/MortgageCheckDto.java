package com.mostafa.ing.mortgage.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Schema(description = "DTO representing mortgage rate details")
@Validated
public record MortgageCheckDto(
        @Schema(description = "Maturity period in years")
        @NotNull(message = "Annual income is required.")
        AmountDto annualIncome,
        @Schema(description = "Maturity period in years", example = "30", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Maturity period is required.")
        Integer maturityPeriod,
        @Schema(description = "Loan value")
        @NotNull(message = "Loan value is required.")
        AmountDto loanValue,
        @Schema(description = "Home value")
        @NotNull(message = "Home value is required.")
        AmountDto homeValue) {
}
