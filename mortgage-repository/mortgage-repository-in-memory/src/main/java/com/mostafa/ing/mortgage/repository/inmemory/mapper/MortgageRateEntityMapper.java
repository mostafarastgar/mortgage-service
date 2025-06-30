package com.mostafa.ing.mortgage.repository.inmemory.mapper;

import com.mostafa.ing.mortgage.model.MortgageRate;
import com.mostafa.ing.mortgage.repository.inmemory.model.MortgageRateEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MortgageRateEntityMapper {
    public static MortgageRate toMortgageRate(final MortgageRateEntity entity) {
        return new MortgageRate(
                entity.maturityPeriod().toPeriod(),
                entity.interestRate(),
                entity.lastUpdate()
        );
    }
}
