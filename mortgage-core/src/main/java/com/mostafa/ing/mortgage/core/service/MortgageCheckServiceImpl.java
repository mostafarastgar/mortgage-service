package com.mostafa.ing.mortgage.core.service;

import com.mostafa.ing.mortgage.core.model.ValidationResult;
import com.mostafa.ing.mortgage.core.service.calculator.MortgagePaymentCalculatorService;
import com.mostafa.ing.mortgage.core.service.validator.MortgageValidatorService;
import com.mostafa.ing.mortgage.exception.ValidationException;
import com.mostafa.ing.mortgage.model.Amount;
import com.mostafa.ing.mortgage.model.Currency;
import com.mostafa.ing.mortgage.model.MortgageCheck;
import com.mostafa.ing.mortgage.model.MortgageCheckResult;
import com.mostafa.ing.mortgage.port.service.MortgageCheckService;
import com.mostafa.ing.mortgage.port.service.MortgageRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.mostafa.ing.mortgage.model.ValidationCode.CODE_INVALID_MATURITY_PERIOD;

@Service
@Slf4j
@RequiredArgsConstructor
public class MortgageCheckServiceImpl implements MortgageCheckService {
    private final MortgageRateService mortgageRateService;
    private final MortgageValidatorService mortgageValidatorService;
    private final MortgagePaymentCalculatorService mortgagePaymentCalculatorService;

    @Override
    public MortgageCheckResult checkMortgage(MortgageCheck mortgageCheck) {
        ValidationResult validationResult = mortgageValidatorService.validateMortgageApplication(mortgageCheck);
        log.debug("mortgage check: {} validationResult: {}", mortgageCheck,
                validationResult);
        if (validationResult.valid()) {
            BigDecimal monthlyPayment =
                    mortgageRateService.findByPeriod(mortgageCheck.maturityPeriod()).map(
                            mortgageRate -> {
                                log.debug("Found mortgage rate for period {}: {}",
                                        mortgageCheck.maturityPeriod(), mortgageRate);
                                return mortgagePaymentCalculatorService.calculateMonthlyPayment(mortgageCheck, mortgageRate);
                            }).orElseThrow(() -> new ValidationException(
                            "No mortgage rate found for the specified maturity period: "
                                    + mortgageCheck.maturityPeriod(), CODE_INVALID_MATURITY_PERIOD));
            return getMortgageCheckResult(mortgageCheck, monthlyPayment);
        } else if(validationResult.validationCode().isBusinessError()) {
            log.debug("Validation failed for mortgage check: {} with validation result: {}",
                    mortgageCheck, validationResult.validationCode());
            return new MortgageCheckResult(false, null,
                    validationResult.validationCode(), validationResult.message());
        } else{
            throw new ValidationException(validationResult.message(), validationResult
                    .validationCode());
        }
    }

    private MortgageCheckResult getMortgageCheckResult(MortgageCheck mortgageCheck, BigDecimal monthlyPayment) {
        ValidationResult validationResult = mortgageValidatorService.
                        validateMonthlyPayment(mortgageCheck, monthlyPayment);
        return new MortgageCheckResult(validationResult.valid(), new Amount(Currency.EUR,
                monthlyPayment), validationResult.validationCode(), validationResult.message());
    }
}
