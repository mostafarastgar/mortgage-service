package com.mostafa.ing.mortgage.presentation.mapper;

import com.mostafa.ing.mortgage.model.MortgageCheckResult;
import com.mostafa.ing.mortgage.presentation.dto.MortgageCheckResultDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MortgageCheckResultMapper {
    public static MortgageCheckResultDto toDto(MortgageCheckResult mortgageCheckResult) {
        return new MortgageCheckResultDto(mortgageCheckResult.feasible(),
                AmountMapper.toDto(mortgageCheckResult.monthlyPayment()));
    }
}
