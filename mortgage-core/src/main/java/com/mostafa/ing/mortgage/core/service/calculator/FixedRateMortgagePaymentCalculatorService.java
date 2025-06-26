package com.mostafa.ing.mortgage.core.service.calculator;

import com.mostafa.ing.mortgage.model.MortgageCheck;
import com.mostafa.ing.mortgage.model.MortgageRate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.mostafa.ing.mortgage.core.util.Constant.MONTHS_IN_YEAR;
import static com.mostafa.ing.mortgage.core.util.Constant.PERCENT;
import static com.mostafa.ing.mortgage.core.util.Constant.ROUNDING_SCALE;

/**
 * Implements a loan amortization formula: M = P * r(1+r)^n/((1+r)^n - 1).
 * Where M is Monthly payment, P is the principal loan amount,
 * r is the monthly interest rate, and n is the number of payments (loan term in months).
 */
@Service
public class FixedRateMortgagePaymentCalculatorService implements MortgagePaymentCalculatorService {

    @Override
    public BigDecimal calculateMonthlyPayment(MortgageCheck mortgageCheck,
                                              MortgageRate mortgageRate) {
        BigDecimal p = mortgageCheck.loanValue().value();
        BigDecimal r = mortgageRate.interestRate().divide(MONTHS_IN_YEAR.multiply(PERCENT),
                ROUNDING_SCALE, RoundingMode.HALF_UP);
        BigDecimal n = new BigDecimal(mortgageCheck.maturityPeriod().toTotalMonths());
        if (BigDecimal.ZERO.equals(mortgageRate.interestRate())) {
            return p.divide(n, RoundingMode.HALF_UP);
        } else if (BigDecimal.ZERO.equals(mortgageCheck.loanValue().value())) {
            return BigDecimal.ZERO;
        }
        return calculate(r, n, p);
    }

    private static BigDecimal calculate(BigDecimal r, BigDecimal n, BigDecimal p) {
        BigDecimal rateOverYears = BigDecimal.ONE.add(r).pow(n.intValue());
        BigDecimal decrementedRateOverYear = rateOverYears.subtract(BigDecimal.ONE);
        return p.multiply(r.multiply(rateOverYears).divide(decrementedRateOverYear,
                ROUNDING_SCALE, RoundingMode.HALF_UP));
    }
}
