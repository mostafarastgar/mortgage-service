package com.mostafa.ing.mortgage;

import com.mostafa.ing.mortgage.presentation.dto.AmountDto;
import com.mostafa.ing.mortgage.presentation.dto.MortgageCheckDto;
import com.mostafa.ing.mortgage.presentation.dto.MortgageCheckResultDto;
import com.mostafa.ing.mortgage.presentation.dto.MortgageRateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


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

    @Test
    void testMortgageCheck() {
        ResponseEntity<MortgageCheckResultDto> response = restTemplate.postForEntity(
                "/v1/api" +
                        "/mortgage-check",
                new MortgageCheckDto(
                        new AmountDto("EUR", "60000.00"),30,
                        new AmountDto("EUR", "240000.00"),
                        new AmountDto("EUR", "260000.00"
                        )), MortgageCheckResultDto.class);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        MortgageCheckResultDto mortgageCheckResultDto = response.getBody();
        assertTrue(mortgageCheckResultDto.feasible());
        assertEquals("1216.04", mortgageCheckResultDto.monthlyCost().value());
    }
}