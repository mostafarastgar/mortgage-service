package com.mostafa.ing.mortgage.repository.inmemory;

import com.mostafa.ing.mortgage.model.MortgageRate;
import com.mostafa.ing.mortgage.port.repository.MortgageRateRepository;
import com.mostafa.ing.mortgage.repository.inmemory.config.InMemoryProperties;
import com.mostafa.ing.mortgage.repository.inmemory.mapper.MortgageRateEntityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class only considers years for the period, e.g. 1 year, 2 years, etc.
 */
@Repository
@Slf4j
public class InMemoryMortgageRateRepository implements MortgageRateRepository {
    private final InMemoryProperties inMemoryProperties;
    private final Map<Integer, MortgageRate> mortgageRateMap;
    private final List<MortgageRate> mortgageRateList;

    public InMemoryMortgageRateRepository(InMemoryProperties inMemoryProperties) {
        this.inMemoryProperties = inMemoryProperties;
        mortgageRateList = this.inMemoryProperties.rates().stream()
                .map(MortgageRateEntityMapper::toMortgageRate).toList();
        mortgageRateMap = mortgageRateList.stream().collect(Collectors.toMap(
                mortgageRate -> mortgageRate.maturityPeriod().getYears(),
                maturityRate -> maturityRate));
    }

    @Override
    public List<MortgageRate> getMortgageRates() {
        log.debug("returning {} mortgage rates.", mortgageRateList.size());
        return mortgageRateList;
    }

    /**
     * @param period period for which the mortgage rate is requested. It only works
     *               with years, e.g. 1 year, 2 years, etc.
     * @return optional mortgage rate for the specified period, empty if not found
     */
    @Override
    public Optional<MortgageRate> findByPeriod(Period period) {
        log.debug("Searching for mortgage rate with year period: {}", period.getYears());
        return Optional.ofNullable(mortgageRateMap.get(period.getYears()));
    }
}
