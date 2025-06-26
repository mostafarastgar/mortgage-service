package com.mostafa.ing.mortgage.core.service;

import com.mostafa.ing.mortgage.model.MortgageRate;
import com.mostafa.ing.mortgage.port.repository.MortgageRateRepository;
import com.mostafa.ing.mortgage.port.service.MortgageRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MortgageRateServiceImpl implements MortgageRateService {
    private final MortgageRateRepository mortgageRateRepository;

    @Override
    public List<MortgageRate> getMortgageRates() {
        log.info("Getting current mortgage rates at {}", OffsetDateTime.now());
        return mortgageRateRepository.getMortgageRates();
    }
}
