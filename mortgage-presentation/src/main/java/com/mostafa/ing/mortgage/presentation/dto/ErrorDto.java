package com.mostafa.ing.mortgage.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO representing error details")
public record ErrorDto(
        @Schema(description = "Error code", example = "4001", requiredMode =
                Schema.RequiredMode.REQUIRED)
        String code,
        @Schema(description = "Error message", example = "Invalid annual income",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String message) {
}
