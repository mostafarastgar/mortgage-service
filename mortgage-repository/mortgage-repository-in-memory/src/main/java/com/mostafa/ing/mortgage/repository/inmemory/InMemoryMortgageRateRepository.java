package com.mostafa.ing.mortgage.repository.inmemory;

import com.mostafa.ing.mortgage.model.MortgageRate;
import com.mostafa.ing.mortgage.port.outbound.repository.MortgageRateRepository;
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
 * This class only considers years and months for the period.
 */
@Repository
@Slf4j
public class InMemoryMortgageRateRepository implements MortgageRateRepository {
    private final InMemoryProperties inMemoryProperties;
    private final Map<Long, MortgageRate> mortgageRateMap;
    private final List<MortgageRate> mortgageRateList;

    public InMemoryMortgageRateRepository(InMemoryProperties inMemoryProperties) {
        this.inMemoryProperties = inMemoryProperties;
        mortgageRateList = this.inMemoryProperties.rates().stream()
                .map(MortgageRateEntityMapper::toMortgageRate).toList();
        mortgageRateMap = mortgageRateList.stream().collect(Collectors.toMap(
                mortgageRate -> mortgageRate.maturityPeriod().toTotalMonths(),
                maturityRate -> maturityRate));
    }

    @Override
    public List<MortgageRate> getMortgageRates() {
        log.debug("returning {} mortgage rates.", mortgageRateList.size());
        return mortgageRateList;
    }

    /**
     * @param period period for which the mortgage rate is requested. It converts the period
     *               to total months to match the keys in the in-memory map.
     * @return optional mortgage rate for the specified period, empty if not found
     */
    @Override
    public Optional<MortgageRate> findByPeriod(Period period) {
        log.debug("Searching for mortgage rate with year period: {}", period);
        return Optional.ofNullable(mortgageRateMap.get(period.toTotalMonths()));
    }
}
