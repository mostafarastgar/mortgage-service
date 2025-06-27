package com.mostafa.ing.mortgage.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO representing if requested mortgage is feasible and the monthly payment")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MortgageCheckResultDto(
        @Schema(description = "Indicates if requested mortgage check is feasible",
                example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
        boolean feasible,
        @Schema(description = "Monthly cost. It is null when a business rule is violated.")
        AmountDto monthlyCost,
        @Schema(description = "Violated business rule code: 41xx.",
                example = "4101")
        String code,
        @Schema(description = "Violated business rule code message.",
                example = "Loan value cannot be greater than home value.")
        String message) {
}
