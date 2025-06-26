package com.mostafa.ing.mortgage.repository.inmemory.mapper;

import com.mostafa.ing.mortgage.model.MortgageRate;
import com.mostafa.ing.mortgage.repository.inmemory.model.MortgageRateEntity;
import lombok.experimental.UtilityClass;

import java.time.Period;

@UtilityClass
public class MortgageRateEntityMapper {
    public static MortgageRate toMortgageRate(final MortgageRateEntity entity) {
        return new MortgageRate(
                Period.ofYears(entity.maturityPeriod()),
                entity.interestRate(),
                entity.lastUpdate()
        );
    }
}
