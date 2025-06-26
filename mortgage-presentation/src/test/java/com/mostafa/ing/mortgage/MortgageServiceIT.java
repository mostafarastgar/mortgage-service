package com.mostafa.ing.mortgage;

import com.mostafa.ing.mortgage.presentation.dto.MortgageRateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MortgageServiceIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetCurrentInterestRates() {
        ResponseEntity<MortgageRateDto[]> response = restTemplate.getForEntity(
                "/v1/api/interest-rates", MortgageRateDto[].class);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        List<MortgageRateDto> rates = List.of(response.getBody());
        assertEquals(3, rates.size());
        assertNotNull(rates.getFirst().interestRate());
        assertEquals(30, rates.getFirst().maturityPeriod());
        assertNotNull(rates.getFirst().lastUpdate());
    }
}