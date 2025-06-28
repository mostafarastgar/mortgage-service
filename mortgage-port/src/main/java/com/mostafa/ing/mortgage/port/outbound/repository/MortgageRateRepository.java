package com.mostafa.ing.mortgage.port.outbound.repository;

import com.mostafa.ing.mortgage.model.MortgageRate;

import java.time.Period;
import java.util.List;
import java.util.Optional;

/**
 * Port interface for accessing mortgage rates repository.
 */
public interface MortgageRateRepository {
    /**
     * @return list of current mortgage rates
     */
    List<MortgageRate> getMortgageRates();

    /**
     * @param period period for which the mortgage rate is requested
     * @return optional mortgage rate for the specified period, empty if not found
     */
    Optional<MortgageRate> findByPeriod(Period period);
}
