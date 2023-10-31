package com.wex.desafio.service.integration;

import com.wex.desafio.model.entity.Purchase;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PurchaseDTO {

    private Long id;
    private String description;
    private LocalDate transactionDate;
    private BigDecimal originalAmountUSD;
    private BigDecimal exchangeRate;
    private BigDecimal convertedAmount;
    private String targetCurrency;

    public PurchaseDTO(Purchase purchase, BigDecimal exchangeRate, BigDecimal convertedAmount, String targetCurrency) {
        this.id = purchase.getId();
        this.description = purchase.getDescription();
        this.transactionDate = purchase.getTransactionDate();
        this.originalAmountUSD = purchase.getPurchaseAmount();
        this.exchangeRate = exchangeRate;
        this.convertedAmount = convertedAmount;
        this.targetCurrency = targetCurrency;
    }
}
