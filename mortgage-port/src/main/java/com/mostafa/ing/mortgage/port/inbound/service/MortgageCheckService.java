package com.mostafa.ing.mortgage.port.inbound.service;

import com.mostafa.ing.mortgage.model.MortgageCheck;
import com.mostafa.ing.mortgage.model.MortgageCheckResult;

/**
 * Port interface for Mortgage Check Service.
 */
public interface MortgageCheckService {
    /**
     * @param mortgageCheck mortgage check details.
     * @return result of the mortgage check, indicating whether the mortgage is feasible
     */
    MortgageCheckResult checkMortgage(MortgageCheck mortgageCheck);
}
