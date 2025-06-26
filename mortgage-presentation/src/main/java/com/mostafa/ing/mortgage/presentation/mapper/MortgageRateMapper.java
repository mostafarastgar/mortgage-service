package com.mostafa.ing.mortgage.presentation.mapper;

import com.mostafa.ing.mortgage.model.MortgageRate;
import com.mostafa.ing.mortgage.presentation.dto.MortgageRateDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MortgageRateMapper {
    public static MortgageRateDto toDto(MortgageRate mortgageRate) {
        return new MortgageRateDto(mortgageRate.maturityPeriod().getYears(),
                mortgageRate.interestRate(),
                mortgageRate.lastUpdate());
    }
}
