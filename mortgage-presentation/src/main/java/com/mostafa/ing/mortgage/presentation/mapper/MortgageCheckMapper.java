package com.mostafa.ing.mortgage.presentation.mapper;

import com.mostafa.ing.mortgage.model.MortgageCheck;
import com.mostafa.ing.mortgage.presentation.dto.MortgageCheckDto;
import lombok.experimental.UtilityClass;

import java.time.Period;

@UtilityClass
public class MortgageCheckMapper {
    public static MortgageCheck toMortgageCheck(MortgageCheckDto mortgageCheckDto) {
        return new MortgageCheck(AmountMapper.toAmount(mortgageCheckDto.annualIncome()),
                Period.ofYears(mortgageCheckDto.maturityPeriod()),
                AmountMapper.toAmount(mortgageCheckDto.loanValue()),
                AmountMapper.toAmount(mortgageCheckDto.homeValue()));
    }
}
