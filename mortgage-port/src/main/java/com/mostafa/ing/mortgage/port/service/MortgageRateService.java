package com.mostafa.ing.mortgage.port.service;

import com.mostafa.ing.mortgage.model.MortgageRate;
import java.util.List;

/**
 * Port interface for Mortgage Rate Service.
 */
public interface MortgageRateService {
    /**
     * @return list of current mortgage rates
     */
    List<MortgageRate> getMortgageRates();
}
