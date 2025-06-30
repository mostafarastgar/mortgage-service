package com.mostafa.ing.mortgage.repository.inmemory;

import com.mostafa.ing.mortgage.model.MortgageRate;
import com.mostafa.ing.mortgage.repository.inmemory.config.InMemoryProperties;
import com.mostafa.ing.mortgage.repository.inmemory.model.MortgagePeriod;
import com.mostafa.ing.mortgage.repository.inmemory.model.MortgageRateEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class InMemoryMortgageRateRepositoryTest {
    @Spy
    private final InMemoryProperties inMemoryProperties =
            new InMemoryProperties(List.of(new MortgageRateEntity(MortgagePeriod.of(Period.of(20, 10, 0)),
                    new BigDecimal("3.5"),
                    OffsetDateTime.now(ZoneId.systemDefault()))));
    @InjectMocks
    private InMemoryMortgageRateRepository subject;

    @Test
    void testGetMortgageRates() {
        List<MortgageRate> mortgageRates = subject.getMortgageRates();
        MortgageRateEntity mortgageRateEntity = inMemoryProperties.rates().getFirst();

        assertEquals(1, mortgageRates.size());
        assertEquals(mortgageRateEntity.interestRate(),
                mortgageRates.getFirst().interestRate());
        assertEquals(mortgageRateEntity.maturityPeriod().toPeriod(),
                mortgageRates.getFirst().maturityPeriod());
        assertEquals(mortgageRateEntity.lastUpdate(),
                mortgageRates.getFirst().lastUpdate());
    }

    @Test
    void testFindByPeriod() {
        MortgageRateEntity mortgageRateEntity = inMemoryProperties.rates().getFirst();
        var period = mortgageRateEntity.maturityPeriod().toPeriod();
        var mortgageRate = subject.findByPeriod(period).orElseThrow();

        assertEquals(mortgageRateEntity.interestRate(),
                mortgageRate.interestRate());
        assertEquals(period, mortgageRate.maturityPeriod());
        assertEquals(mortgageRateEntity.lastUpdate(),
                mortgageRate.lastUpdate());
    }

    @Test
    void testNulFindByPeriod() {
        var period = Period.ofMonths(1);
        var mortgageRate = subject.findByPeriod(period);

        assertTrue(mortgageRate.isEmpty());
    }
}