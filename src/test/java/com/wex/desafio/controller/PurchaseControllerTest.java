package com.wex.desafio.controller;

import com.wex.desafio.model.dto.PurchaseSaveDTO;
import com.wex.desafio.model.entity.Purchase;
import com.wex.desafio.service.PurchaseService;
import com.wex.desafio.service.integration.PurchaseDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
 class PurchaseControllerTest {

    @InjectMocks
    private PurchaseController purchaseController;

    @Mock
    private PurchaseService purchaseService;

    @Test
     void testStorePurchase() {
        PurchaseSaveDTO purchaseDTO = new PurchaseSaveDTO("teste", LocalDate.now(), BigDecimal.ONE);
        Purchase purchase = purchaseDTO.toEntity();
        when(purchaseService.savePurchase(purchase)).thenReturn(purchase);

        ResponseEntity<Purchase> response = purchaseController.storePurchase(purchaseDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
     void testRetrievePurchase() {
        Long id = 1L;
        Purchase purchase = new Purchase();
        when(purchaseService.getPurchase(id)).thenReturn(Optional.of(purchase));

        ResponseEntity<Purchase> response = purchaseController.retrievePurchase(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
     void testRetrievePurchaseWithConversion() {
        Long id = 1L;
        String currency = "USD";
        PurchaseDTO purchaseDTO = new PurchaseDTO();

        when(purchaseService.retrievePurchaseWithConversion(id)).thenReturn(purchaseDTO);

        ResponseEntity<PurchaseDTO> response = purchaseController.retrievePurchaseWithConversion(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
     void testRetrievePurchaseNotFound() {
        Long id = 1L;

        when(purchaseService.getPurchase(id)).thenReturn(Optional.empty());

        ResponseEntity<Purchase> response = purchaseController.retrievePurchase(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
     void testRetrievePurchaseWithConversionError() {
        Long id = 1L;
        when(purchaseService.retrievePurchaseWithConversion(id)).thenThrow(new RuntimeException("Error retrieving conversion"));

        try {
            ResponseEntity<PurchaseDTO> response = purchaseController.retrievePurchaseWithConversion(id);
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }
}
