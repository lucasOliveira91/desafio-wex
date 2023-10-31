package com.wex.desafio.service.integration.dto;

import lombok.Data;

@Data
public class ExchangeRateItem {
    private String countryCurrencyDesc;
    private String exchangeRate;
    private String recordDate;
}
