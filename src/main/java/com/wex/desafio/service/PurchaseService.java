package com.wex.desafio.service;

import com.wex.desafio.model.entity.Purchase;
import com.wex.desafio.repository.PurchaseRepository;
import com.wex.desafio.service.integration.ExchangeRateClient;
import com.wex.desafio.service.integration.PurchaseDTO;
import com.wex.desafio.service.integration.dto.ExchangeRateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PurchaseService {

    public static final String TARGET_CURRENCY = "Canada-Dollar";

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ExchangeRateClient exchangeRateClient;

    public Purchase savePurchase(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    public Optional<Purchase> getPurchase(Long id) {
        return purchaseRepository.findById(id);
    }

    public PurchaseDTO retrievePurchaseWithConversion(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() -> new RuntimeException("Purchase not found"));

        ExchangeRateResponse response = exchangeRateClient.getExchangeRate();
        BigDecimal rate = new BigDecimal(response.getData().get(0).getExchangeRate());

        BigDecimal convertedAmount = purchase.getPurchaseAmount().multiply(rate);
        return new PurchaseDTO(purchase, rate, convertedAmount, TARGET_CURRENCY);
    }

}
