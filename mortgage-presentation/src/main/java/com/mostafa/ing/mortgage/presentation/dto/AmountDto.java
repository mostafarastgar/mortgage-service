package com.mostafa.ing.mortgage.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;

@Schema(description = "DTO representing amount with currency.")
@Validated
public record AmountDto(
        @Schema(description = "Currency, only Euro is supported at the moment.",
                example = "EUR")
        @NotNull(message = "Currency is required.")
        @Pattern(regexp = "EUR", message = "Only EUR is supported as currency.")
        String currency,
        @Schema(description = "Decimal value representing the amount",
                example = "30000.00")
        @NotNull(message = "Value is required.")
        @Pattern(regexp = "\\d+(\\.\\d{1,2})?", message = "Value must be a valid decimal number with up to two decimal places.")
        String value) {
}
