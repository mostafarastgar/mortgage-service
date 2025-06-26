package com.mostafa.ing.mortgage.presentation.controller;


import com.mostafa.ing.mortgage.port.service.MortgageCheckService;
import com.mostafa.ing.mortgage.presentation.dto.MortgageCheckDto;
import com.mostafa.ing.mortgage.presentation.dto.MortgageCheckResultDto;
import com.mostafa.ing.mortgage.presentation.mapper.MortgageCheckMapper;
import com.mostafa.ing.mortgage.presentation.mapper.MortgageCheckResultMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MortgageCheckControllerImpl implements MortgageCheckController {
    private final MortgageCheckService mortgageCheckService;

    @Override
    public MortgageCheckResultDto mortgageCheck(MortgageCheckDto mortgageCheckDto) {
        return MortgageCheckResultMapper.toDto(mortgageCheckService
                .checkMortgage(MortgageCheckMapper.toMortgageCheck(mortgageCheckDto)));
    }
}
