package com.mostafa.ing.mortgage.presentation.controller;

import com.mostafa.ing.mortgage.presentation.dto.MortgageRateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Interest Rate api", description = "Operations related to Mortgage Interest Rates")
@RequestMapping("/v1/api/interest-rates")
public interface MortgageRateController {
    @GetMapping
    @Operation(summary = "Get current interest rates", description = "Returns the current mortgage interest rates")
    List<MortgageRateDto> getCurrentInterestRates();

}
