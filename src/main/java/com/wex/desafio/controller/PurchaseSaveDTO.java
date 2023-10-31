package com.wex.desafio.controller;

import com.wex.desafio.model.entity.Purchase;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PurchaseSaveDTO {

    private Long id;

    @Size(max = 50, message = "Description util 50 characters")
    private String description;

    @NotNull(message = "Transaction date is required and should be a valid date format")
    private LocalDate transactionDate;

    @NotNull(message = "Purchase amount is required")
    @DecimalMin(value = "0.01", message = "Purchase amount must be a positive value")
    @Digits(integer=12, fraction=2, message = "Purchase amount should be valid")
    private BigDecimal purchaseAmount;

    public PurchaseSaveDTO(String description, LocalDate transactionDate, BigDecimal purchaseAmount) {
        this.description = description;
        this.transactionDate = transactionDate;
        this.purchaseAmount = purchaseAmount;
    }

    public Purchase toEntity() {
        return new Purchase(this.description, this.transactionDate, this.purchaseAmount);
    }
}
