package com.mostafa.ing.mortgage.repository.inmemory;

import com.mostafa.ing.mortgage.model.MortgageRate;
import com.mostafa.ing.mortgage.port.repository.MortgageRateRepository;
import com.mostafa.ing.mortgage.repository.inmemory.config.InMemoryProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class InMemoryMortgageRateRepository implements MortgageRateRepository {
    private final InMemoryProperties inMemoryProperties;
    private final Map<Period, MortgageRate> mortgageRateMap;
    private final List<MortgageRate> mortgageRateList;

    public InMemoryMortgageRateRepository(InMemoryProperties inMemoryProperties) {
        this.inMemoryProperties = inMemoryProperties;
        mortgageRateList = this.inMemoryProperties.rates().stream()
                .map(entity -> new MortgageRate(
                        Period.ofYears(entity.maturityPeriod()),
                        entity.interestRate(),
                        entity.lastUpdate()
                )).toList();
        mortgageRateMap =
                mortgageRateList.stream().collect(Collectors.toMap(MortgageRate::maturityPeriod,
                        maturityRate -> maturityRate));
    }

    @Override
    public List<MortgageRate> getMortgageRates() {
        log.debug("returning {} mortgage rates.", mortgageRateList.size());
        return mortgageRateList;
    }
}
