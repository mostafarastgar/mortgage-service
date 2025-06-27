package com.mostafa.ing.mortgage.core.service.calculator;

import com.mostafa.ing.mortgage.model.MortgageCheck;
import com.mostafa.ing.mortgage.model.MortgageRate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

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
    private final Map<RateKey, Double> rateOverYearsCache = new ConcurrentHashMap<>();

    @Override
    public BigDecimal calculateMonthlyPayment(MortgageCheck mortgageCheck,
                                              MortgageRate mortgageRate) {
        BigDecimal p = mortgageCheck.loanValue().value();
        BigDecimal r = mortgageRate.interestRate().divide(MONTHS_IN_YEAR.multiply(PERCENT),
                ROUNDING_SCALE, RoundingMode.HALF_UP);
        BigDecimal n = new BigDecimal(mortgageCheck.maturityPeriod().toTotalMonths());
        if (BigDecimal.ZERO.equals(mortgageRate.interestRate())) {
            return p.divide(n, ROUNDING_SCALE, RoundingMode.HALF_UP);
        } else if (BigDecimal.ZERO.equals(mortgageCheck.loanValue().value())) {
            return BigDecimal.ZERO;
        }
        return calculate(r, n, p);
    }

    private BigDecimal calculate(BigDecimal r, BigDecimal n, BigDecimal p) {
//      Implements a loan amortization formula: M = P * r(1+r)^n/((1+r)^n - 1).

        double rateOverYearsDouble = getRateOverYears(r, n);
        BigDecimal rateOverYears = BigDecimal.valueOf(rateOverYearsDouble);

        BigDecimal numerator = r.multiply(rateOverYears);
        BigDecimal denominator = rateOverYears.subtract(BigDecimal.ONE);
        if (denominator.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return p.multiply(
                numerator.divide(denominator, ROUNDING_SCALE, RoundingMode.HALF_UP)
        );
    }

    private double getRateOverYears(BigDecimal r, BigDecimal n) {
        RateKey key = new RateKey(r, n);
        // Calculate (1 + r)^n
        // Using double for exponentiation introduces slight floating-point error.
        // However, for practical mortgage payments, this difference is negligible (e.g.
        // < a cent).
        // Meanwhile, Math.pow is significantly faster than BigDecimal's pow method.
        return rateOverYearsCache.computeIfAbsent(key, k -> Math.pow(
                BigDecimal.ONE.add(r).doubleValue(),
                n.doubleValue()));
    }

    private static class RateKey {
        private final BigDecimal rate;
        private final BigDecimal period;

        public RateKey(BigDecimal rate, BigDecimal period) {
            this.rate = rate.stripTrailingZeros();
            this.period = period.stripTrailingZeros();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof RateKey key)) return false;
            return rate.compareTo(key.rate) == 0 &&
                    period.compareTo(key.period) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rate, period);
        }
    }
}
