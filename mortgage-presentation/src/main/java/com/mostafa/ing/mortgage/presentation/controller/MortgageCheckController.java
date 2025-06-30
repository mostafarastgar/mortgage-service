package com.mostafa.ing.mortgage.presentation.controller;

import com.mostafa.ing.mortgage.presentation.dto.MortgageCheckDto;
import com.mostafa.ing.mortgage.presentation.dto.MortgageCheckResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Checks if mortgage feasible and returns the monthly payment", description =
        "Checks the feasibility of the mortgage and returns the monthly payment " +
                "according to the current interest rates")
@RequestMapping("/v1/api/mortgage-check")
public interface MortgageCheckController {
    @PostMapping
    @Operation(summary = "check the feasibility of the mortgage", description =
            "Returns if mortgage is feasible and the monthly payment according to the current interest rates")
    MortgageCheckResultDto mortgageCheck(
            @RequestBody
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description =
                    "Mortgage check request", required = true)
            MortgageCheckDto mortgageCheckDto);

}
