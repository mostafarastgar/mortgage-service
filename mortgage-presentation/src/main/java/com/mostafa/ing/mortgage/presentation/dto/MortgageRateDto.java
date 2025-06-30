package com.mostafa.ing.mortgage.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Schema(description = "DTO representing mortgage rate details")
public record MortgageRateDto(
        @Schema(description = "Maturity period in years", example = "30", requiredMode = Schema.RequiredMode.REQUIRED)
        int maturityPeriod,
        @Schema(description = "Interest rate for the mortgage", example = "3.5", requiredMode = Schema.RequiredMode.REQUIRED)
        BigDecimal interestRate,
        @Schema(description = "Last update timestamp", example = "2023-10-01T12:00:00Z", requiredMode = Schema.RequiredMode.REQUIRED)
        OffsetDateTime lastUpdate) {
}
