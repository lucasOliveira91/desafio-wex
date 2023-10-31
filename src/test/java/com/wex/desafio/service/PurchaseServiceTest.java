package com.wex.desafio.service;

import com.wex.desafio.model.entity.Purchase;
import com.wex.desafio.repository.PurchaseRepository;
import com.wex.desafio.service.integration.ExchangeRateClient;
import com.wex.desafio.service.integration.PurchaseDTO;
import com.wex.desafio.service.integration.dto.ExchangeData;
import com.wex.desafio.service.integration.dto.ExchangeRateResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
 class PurchaseServiceTest {

    @InjectMocks
    private PurchaseService purchaseService;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private ExchangeRateClient exchangeRateClient;

    @Test
     void testSavePurchase() {
        Purchase purchase = new Purchase();
        when(purchaseRepository.save(purchase)).thenReturn(purchase);

        Purchase savedPurchase = purchaseService.savePurchase(purchase);
        assertEquals(purchase, savedPurchase);
    }

    @Test
     void testGetPurchase() {
        Long id = 1L;
        Purchase purchase = new Purchase();
        when(purchaseRepository.findById(id)).thenReturn(Optional.of(purchase));

        Optional<Purchase> retrievedPurchase = purchaseService.getPurchase(id);
        assertTrue(retrievedPurchase.isPresent());
        assertEquals(purchase, retrievedPurchase.get());
    }

    @Test
     void testRetrievePurchaseWithConversion() {
        Long id = 1L;
        String currency = "USD";
        LocalDate transactionDate = LocalDate.now();

        Purchase purchase = new Purchase();
        purchase.setPurchaseAmount(new BigDecimal("100"));
        purchase.setTransactionDate(transactionDate);

        when(purchaseRepository.findById(id)).thenReturn(Optional.of(purchase));

        ExchangeData rate = new ExchangeData();
        rate.setCountryCurrencyDesc(currency);
        rate.setExchangeRate(new BigDecimal("1.5").toString());
        rate.setRecordDate(transactionDate);

        ExchangeRateResponse response = new ExchangeRateResponse();
        response.setData(Collections.singletonList(rate));

        when(exchangeRateClient.getExchangeRate()).thenReturn(response);

        PurchaseDTO purchaseDTO = purchaseService.retrievePurchaseWithConversion(id);

        assertEquals(new BigDecimal("150.0"), purchaseDTO.getConvertedAmount());
    }

    @Test
     void testRetrievePurchaseNotFound() {
        Long id = 1L;
        when(purchaseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> purchaseService.retrievePurchaseWithConversion(id));
    }
}
