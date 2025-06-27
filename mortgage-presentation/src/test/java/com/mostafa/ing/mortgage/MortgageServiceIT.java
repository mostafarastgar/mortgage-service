package com.mostafa.ing.mortgage;

import com.mostafa.ing.mortgage.exception.ValidationException;
import com.mostafa.ing.mortgage.port.service.MortgageCheckService;
import com.mostafa.ing.mortgage.presentation.dto.AmountDto;
import com.mostafa.ing.mortgage.presentation.dto.ErrorDto;
import com.mostafa.ing.mortgage.presentation.dto.MortgageCheckDto;
import com.mostafa.ing.mortgage.presentation.dto.MortgageCheckResultDto;
import com.mostafa.ing.mortgage.presentation.dto.MortgageRateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.List;

import static com.mostafa.ing.mortgage.model.ValidationCode.CODE_INVALID_BASED_ON_HOME_VALUE;
import static com.mostafa.ing.mortgage.model.ValidationCode.CODE_INVALID_INCOME;
import static com.mostafa.ing.mortgage.presentation.controller.ValidationExceptionHandler.INTERNAL_SERVER_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MortgageServiceIT {

    @MockitoSpyBean
    private MortgageCheckService mortgageCheckService;

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
                        new AmountDto("EUR", "60000.00"), 30,
                        new AmountDto("EUR", "240000.00"),
                        new AmountDto("EUR", "260000.00"
                        )), MortgageCheckResultDto.class);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        MortgageCheckResultDto mortgageCheckResultDto = response.getBody();
        assertTrue(mortgageCheckResultDto.feasible());
        assertEquals("1216.04", mortgageCheckResultDto.monthlyCost().value());
    }

    @Test
    void testMortgageCheckBusinessValidationError() {
        ResponseEntity<MortgageCheckResultDto> response = restTemplate.postForEntity(
                "/v1/api" +
                        "/mortgage-check",
                new MortgageCheckDto(
                        new AmountDto("EUR", "60000.00"), 30,
                        new AmountDto("EUR", "270000.00"),
                        new AmountDto("EUR", "260000.00"
                        )), MortgageCheckResultDto.class);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        MortgageCheckResultDto mortgageCheckResultDto = response.getBody();
        assertFalse(mortgageCheckResultDto.feasible());
        assertEquals(CODE_INVALID_BASED_ON_HOME_VALUE.getValue(),
                mortgageCheckResultDto.code());
    }

    @Test
    void testMortgageCheckValidationError() {
        ResponseEntity<ErrorDto> response = restTemplate.postForEntity(
                "/v1/api" +
                        "/mortgage-check",
                new MortgageCheckDto(
                        new AmountDto("EUR", "60000.175"), 30,
                        new AmountDto("EUR", "240000.00"),
                        new AmountDto("EUR", "260000.00"
                        )), ErrorDto.class);
        assertEquals(400, response.getStatusCode().value());
        assertNotNull(response.getBody());
        ErrorDto errorDto = response.getBody();
        assertEquals("400", errorDto.code());
    }

    @Test
    void testMortgageCheckCoreValidationError() {
        doThrow(new ValidationException("Income must be greater than zero.", CODE_INVALID_INCOME))
                .when(mortgageCheckService).checkMortgage(any());
        ResponseEntity<ErrorDto> response = restTemplate.postForEntity(
                "/v1/api" +
                        "/mortgage-check",
                new MortgageCheckDto(
                        new AmountDto("EUR", "60000.00"), 30,
                        new AmountDto("EUR", "240000.00"),
                        new AmountDto("EUR", "260000.00"
                        )), ErrorDto.class);
        assertEquals(400, response.getStatusCode().value());
        assertNotNull(response.getBody());
        ErrorDto errorDto = response.getBody();
        assertEquals(CODE_INVALID_INCOME.getValue(), errorDto.code());
    }

    @Test
    void testMortgageCheckInternalError() {
        doThrow(new RuntimeException("Internal error occurred"))
                .when(mortgageCheckService).checkMortgage(any());
        ResponseEntity<ErrorDto> response = restTemplate.postForEntity(
                "/v1/api" +
                        "/mortgage-check",
                new MortgageCheckDto(
                        new AmountDto("EUR", "60000.00"), 30,
                        new AmountDto("EUR", "240000.00"),
                        new AmountDto("EUR", "260000.00"
                        )), ErrorDto.class);
        assertEquals(500, response.getStatusCode().value());
        assertNotNull(response.getBody());
        ErrorDto errorDto = response.getBody();
        assertEquals("500", errorDto.code());
        assertEquals(INTERNAL_SERVER_ERROR_MESSAGE, errorDto.message());
    }
}