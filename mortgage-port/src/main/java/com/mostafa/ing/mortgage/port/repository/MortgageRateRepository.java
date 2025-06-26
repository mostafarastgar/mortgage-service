package com.mostafa.ing.mortgage.port.repository;

import com.mostafa.ing.mortgage.model.MortgageRate;

import java.util.List;

/**
 * Port interface for accessing mortgage rates repository.
 */
public interface MortgageRateRepository {
    /**
     * @return list of current mortgage rates
     */
    List<MortgageRate> getMortgageRates();
}
