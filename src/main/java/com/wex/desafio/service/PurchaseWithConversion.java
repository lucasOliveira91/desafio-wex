package com.wex.desafio.service;

import com.wex.desafio.model.entity.Purchase;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseWithConversion extends Purchase {
    private BigDecimal exchangeRate;
    private BigDecimal convertedAmount;

    public PurchaseWithConversion(Purchase purchase, BigDecimal exchangeRate, BigDecimal convertedAmount) {
        super(purchase.getDescription(), purchase.getTransactionDate(), purchase.getPurchaseAmount());
        this.exchangeRate = exchangeRate;
        this.convertedAmount = convertedAmount;
    }
}
