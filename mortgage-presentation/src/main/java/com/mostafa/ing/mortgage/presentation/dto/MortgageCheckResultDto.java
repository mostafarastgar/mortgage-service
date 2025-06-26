package com.mostafa.ing.mortgage.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO representing if requested mortgage is feasible and the monthly payment")
public record MortgageCheckResultDto(
        @Schema(description = "Indicates if requested mortgage check is feasible",
                example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
        boolean feasible,
        @Schema(description = "Monthly cost", requiredMode = Schema.RequiredMode.REQUIRED)
        AmountDto monthlyCost) {
}
