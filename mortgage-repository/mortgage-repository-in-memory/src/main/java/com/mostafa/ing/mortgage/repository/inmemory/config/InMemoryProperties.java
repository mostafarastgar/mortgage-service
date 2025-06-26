package com.mostafa.ing.mortgage.repository.inmemory.config;

import com.mostafa.ing.mortgage.repository.inmemory.model.MortgageRateEntity;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "mortgage.repository.in-memory")
public record InMemoryProperties(List<MortgageRateEntity> rates) {
}