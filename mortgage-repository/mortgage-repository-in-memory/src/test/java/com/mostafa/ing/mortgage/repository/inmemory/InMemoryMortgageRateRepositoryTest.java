package com.mostafa.ing.mortgage.repository.inmemory;

import com.mostafa.ing.mortgage.model.MortgageRate;
import com.mostafa.ing.mortgage.repository.inmemory.config.InMemoryProperties;
import com.mostafa.ing.mortgage.repository.inmemory.model.MortgageRateEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class InMemoryMortgageRateRepositoryTest {
    @Spy
    private final InMemoryProperties inMemoryProperties =
            new InMemoryProperties(List.of(new MortgageRateEntity(30,
                    new BigDecimal("3.5"),
                    OffsetDateTime.now(ZoneId.systemDefault()))));
    @InjectMocks
    private InMemoryMortgageRateRepository subject;
    @Test
    void testGetMortgageRates() {
        List<MortgageRate> mortgageRates = subject.getMortgageRates();

        assertEquals(1, mortgageRates.size());
        assertEquals(inMemoryProperties.rates().getFirst().interestRate(),
                mortgageRates.getFirst().interestRate());
        assertEquals(inMemoryProperties.rates().getFirst().maturityPeriod(),
                mortgageRates.getFirst().maturityPeriod().getYears());
        assertEquals(inMemoryProperties.rates().getFirst().lastUpdate(),
                mortgageRates.getFirst().lastUpdate());
    }
}