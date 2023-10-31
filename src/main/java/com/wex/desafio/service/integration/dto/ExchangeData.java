package com.wex.desafio.service.integration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExchangeData {

    @JsonProperty("country_currency_desc")
    private String countryCurrencyDesc;

    @JsonProperty("exchange_rate")
    private String exchangeRate = "1.252";

    @JsonProperty("record_date")
    private LocalDate recordDate;
}
