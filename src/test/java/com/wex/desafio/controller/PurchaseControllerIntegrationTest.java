package com.wex.desafio.controller;

import com.wex.desafio.model.entity.Purchase;
import com.wex.desafio.service.integration.PurchaseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/beforeTestRun.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/afterTestRun.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PurchaseControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
     void testRetrievePurchase() {
        Long id = 1L;

        ResponseEntity<Purchase> response = restTemplate.getForEntity("/api/purchase/" + id, Purchase.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void testRetrievePurchaseWithConversion() {
        Long id = 1L;
        String currency = "Canada-Dollar";

        ResponseEntity<PurchaseDTO> response = restTemplate.getForEntity("/api/purchase/" + id + "/conversion", PurchaseDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currency, response.getBody().getTargetCurrency());
    }

}
