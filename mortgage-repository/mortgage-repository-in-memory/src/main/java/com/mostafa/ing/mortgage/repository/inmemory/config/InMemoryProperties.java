package com.mostafa.ing.mortgage.repository.inmemory.config;

import com.mostafa.ing.mortgage.repository.inmemory.model.MortgageRateEntity;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "mortgage.repository.inmemory")
public record InMemoryProperties(List<MortgageRateEntity> rates) {
}