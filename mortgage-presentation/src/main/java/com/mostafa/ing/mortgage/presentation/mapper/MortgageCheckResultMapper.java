package com.mostafa.ing.mortgage.presentation.mapper;

import com.mostafa.ing.mortgage.model.MortgageCheckResult;
import com.mostafa.ing.mortgage.presentation.dto.MortgageCheckResultDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MortgageCheckResultMapper {
    public static MortgageCheckResultDto toDto(MortgageCheckResult mortgageCheckResult) {
        if (mortgageCheckResult.feasible()) {
            return new MortgageCheckResultDto(true,
                    AmountMapper.toDto(mortgageCheckResult.monthlyPayment()), null, null);
        } else {
            return new MortgageCheckResultDto(false,null,
                    mortgageCheckResult.code().getValue(), mortgageCheckResult.message());

        }
    }
}
