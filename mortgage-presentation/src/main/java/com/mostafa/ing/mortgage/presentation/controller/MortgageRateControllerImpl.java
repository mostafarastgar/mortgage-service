package com.mostafa.ing.mortgage.presentation.controller;

import com.mostafa.ing.mortgage.port.inbound.service.MortgageRateService;
import com.mostafa.ing.mortgage.presentation.dto.MortgageRateDto;
import com.mostafa.ing.mortgage.presentation.mapper.MortgageRateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MortgageRateControllerImpl implements MortgageRateController {
    private final MortgageRateService mortgageRateService;

    @Override
    public List<MortgageRateDto> getCurrentInterestRates() {
        return mortgageRateService.getMortgageRates().stream()
                .map(MortgageRateMapper::toDto)
                .toList();
    }
}
