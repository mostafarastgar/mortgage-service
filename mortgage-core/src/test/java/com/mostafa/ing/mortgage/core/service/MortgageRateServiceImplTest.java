package com.mostafa.ing.mortgage.core.service;

import com.mostafa.ing.mortgage.model.MortgageRate;
import com.mostafa.ing.mortgage.port.repository.MortgageRateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.Period;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MortgageRateServiceImplTest {
    @Mock
    private MortgageRateRepository mortgageRateRepository;
    @InjectMocks
    private MortgageRateServiceImpl subject;

    @Test
    void testGetMortgageRates() {
        MortgageRate mortgageRate = new MortgageRate(Period.ofYears(30),
                new BigDecimal("3.5"),
                OffsetDateTime.now());
        when(mortgageRateRepository.getMortgageRates()).thenReturn(List.of(mortgageRate));

        subject.getMortgageRates();

        assertEquals(1, subject.getMortgageRates().size());
        assertEquals(mortgageRate.interestRate(),
                subject.getMortgageRates().getFirst().interestRate());
        assertEquals(mortgageRate.maturityPeriod(),
                subject.getMortgageRates().getFirst().maturityPeriod());
        assertEquals(mortgageRate.lastUpdate(),
                subject.getMortgageRates().getFirst().lastUpdate());
    }
}