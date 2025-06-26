package com.mostafa.ing.mortgage.core.service.validator;

import com.mostafa.ing.mortgage.core.model.ValidationResult;
import com.mostafa.ing.mortgage.model.Currency;
import com.mostafa.ing.mortgage.model.MortgageCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.mostafa.ing.mortgage.core.util.Constant.INCOME_MULTIPLICAND;
import static com.mostafa.ing.mortgage.core.util.Constant.MONTHS_IN_YEAR;
import static com.mostafa.ing.mortgage.core.util.Constant.ROUNDING_SCALE;
import static com.mostafa.ing.mortgage.model.ValidationCode.CODE_INVALID_BASED_ON_HOME_VALUE;
import static com.mostafa.ing.mortgage.model.ValidationCode.CODE_INVALID_BASED_ON_MAXIMUM_ALLOWED;
import static com.mostafa.ing.mortgage.model.ValidationCode.CODE_INVALID_CURRENCY;
import static com.mostafa.ing.mortgage.model.ValidationCode.CODE_INVALID_INCOME;
import static com.mostafa.ing.mortgage.model.ValidationCode.CODE_INVALID_MATURITY_PERIOD_MONTHS;
import static com.mostafa.ing.mortgage.model.ValidationCode.CODE_NEGATIVE_HOME_VALUE;
import static com.mostafa.ing.mortgage.model.ValidationCode.CODE_NEGATIVE_LOAD_VALUE;
import static com.mostafa.ing.mortgage.model.ValidationCode.CODE_TOO_BIG_MATURITY_PERIOD_MONTHS;
import static com.mostafa.ing.mortgage.model.ValidationCode.CODE_TOO_LOW_INCOME;
import static com.mostafa.ing.mortgage.model.ValidationCode.CODE_VALID;

@Service
@Slf4j
public class BasicMortgageCheckValidatorService implements MortgageValidatorService {
    @Override
    public ValidationResult validateMortgageApplication(MortgageCheck mortgageCheck) {
        if (!Currency.EUR.equals(mortgageCheck.loanValue().currency())) {
            return new ValidationResult(false, CODE_INVALID_CURRENCY,
                    "Invalid loan currency, only Euro (EUR) is supported.");
        } else if (!Currency.EUR.equals(mortgageCheck.homeValue().currency())) {
            return new ValidationResult(false, CODE_INVALID_CURRENCY,
                    "Invalid home value currency, only Euro (EUR) is supported.");
        } else if (!Currency.EUR.equals(mortgageCheck.income().currency())) {
            return new ValidationResult(false, CODE_INVALID_CURRENCY,
                    "Invalid income currency, only Euro (EUR) is supported.");
        } else if (BigDecimal.ZERO.compareTo(mortgageCheck.homeValue().value()) >= 0) {
            return new ValidationResult(false, CODE_NEGATIVE_HOME_VALUE,
                    "Home value must be greater than zero.");
        } else if (BigDecimal.ZERO.compareTo(mortgageCheck.loanValue().value()) > 0) {
            return new ValidationResult(false, CODE_NEGATIVE_LOAD_VALUE,
                    "Loan value must be greater than zero.");
        } else if (mortgageCheck.loanValue().value().compareTo(mortgageCheck.homeValue().value()) > 0) {
            return new ValidationResult(false, CODE_INVALID_BASED_ON_HOME_VALUE,
                    "Loan value cannot be greater than home value.");
        } else {
            if (mortgageCheck.loanValue().value()
                    .compareTo(mortgageCheck.income().value().multiply(INCOME_MULTIPLICAND)) > 0) {
                return new ValidationResult(false, CODE_INVALID_BASED_ON_MAXIMUM_ALLOWED,
                        "Loan value cannot be more than 4 times the income.");
            } else if (BigDecimal.ZERO.compareTo(mortgageCheck.income().value()) >= 0) {
                return new ValidationResult(false, CODE_INVALID_INCOME,
                        "Income must be greater than zero.");
            } else if (mortgageCheck.maturityPeriod().toTotalMonths() <= 0) {
                return new ValidationResult(false, CODE_INVALID_MATURITY_PERIOD_MONTHS,
                        "Maturity period must be greater than zero months.");
            } else if (mortgageCheck.maturityPeriod().toTotalMonths() > Integer.MAX_VALUE) {
                return new ValidationResult(false, CODE_TOO_BIG_MATURITY_PERIOD_MONTHS,
                        "Maturity period exceeds maximum allowed months.");
            }
        }
        return new ValidationResult(true, CODE_VALID, "Mortgage application is valid.");
    }

    @Override
    public ValidationResult validateMonthlyPayment(MortgageCheck mortgageCheck, BigDecimal monthlyPayment) {
        BigDecimal montlyIncome = mortgageCheck.income().value().divide(MONTHS_IN_YEAR, ROUNDING_SCALE, RoundingMode.HALF_UP);
        if (montlyIncome.compareTo(monthlyPayment) < 0) {
            log.info("too low monthly income: {} for loan monthly payment: {}",
                    montlyIncome, monthlyPayment);
            return new ValidationResult(false, CODE_TOO_LOW_INCOME,
                    "Monthly payment exceeds the monthly income.");
        }
        return new ValidationResult(true, CODE_VALID, "Monthly payment is affordable.");

    }
}
