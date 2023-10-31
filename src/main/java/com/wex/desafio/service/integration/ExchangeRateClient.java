package com.wex.desafio.service.integration;

import com.wex.desafio.FeignConfig;
import com.wex.desafio.service.integration.dto.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "exchangeRateClient", url = "https://api.fiscaldata.treasury.gov/services/api/fiscal_service/v1/accounting/od", configuration = FeignConfig.class)
public interface ExchangeRateClient {

    @GetMapping("/rates_of_exchange?fields=country_currency_desc,exchange_rate,record_date&filter=country_currency_desc:in:(Canada-Dollar),record_date:gte:2020-01-01")
    ExchangeRateResponse getExchangeRate();
}
