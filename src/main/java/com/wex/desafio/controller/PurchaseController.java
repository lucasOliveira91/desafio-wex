package com.wex.desafio.controller;

import com.wex.desafio.model.entity.Purchase;
import com.wex.desafio.service.PurchaseService;
import com.wex.desafio.service.integration.PurchaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<Purchase> storePurchase(@RequestBody PurchaseSaveDTO purchaseDTO) {
        try {
            Purchase purchase = purchaseDTO.toEntity();
            Purchase savedPurchase = purchaseService.savePurchase(purchase);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPurchase);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error storing the purchase", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> retrievePurchase(@PathVariable Long id) {
        Optional<Purchase> purchase = purchaseService.getPurchase(id);
        return purchase.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/conversion")
    public ResponseEntity<PurchaseDTO> retrievePurchaseWithConversion(@PathVariable Long id) {
        try {
            PurchaseDTO purchaseWithConversion = purchaseService.retrievePurchaseWithConversion(id);
            return ResponseEntity.ok(purchaseWithConversion);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.", e);
        }
    }
}
