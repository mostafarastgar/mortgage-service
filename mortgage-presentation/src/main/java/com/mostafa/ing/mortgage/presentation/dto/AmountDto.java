package com.mostafa.ing.mortgage.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Schema(description = "DTO representing amount with currency.")
public record AmountDto(
        @Schema(description = "Currency, only Euro is supported at the moment.",
                example = "EUR")
        @NotNull(message = "Currency is required.")
        @Pattern(regexp = "EUR", message = "Only EUR is supported as currency.")
        String currency,
        @Schema(description = "Decimal value representing the amount",
                example = "30000.00")
        @NotNull(message = "Value is required.")
        @Max(value = 10_000_000, message = "Value cannot exceed 10 million.")
        @Digits(integer = 10, fraction = 2, message = "Value must be a valid decimal " +
                "number with up to 8 digits before the decimal point and two digits after.")
        @Positive(message = "Value must be a positive number.")
        String value) {
}
