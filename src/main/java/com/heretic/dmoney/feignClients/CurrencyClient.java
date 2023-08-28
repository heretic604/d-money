package com.heretic.dmoney.feignClients;

import com.heretic.dmoney.dto.responses.CurrencyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Receives data on currencies from the National Bank of the Republic of Belarus (nbrb.by)
 */
@FeignClient(value = "currencyClient", url = "${client.currency.url}")
public interface CurrencyClient {

    /**
     * Get currency data
     *
     * @param currency currency name (RUB, EUR, AUD etc.)
     * @return currency info
     */
    @GetMapping("/{currency}?parammode=2")
    CurrencyResponse getCurrency(@PathVariable String currency);

    /**
     * Get all available currency data
     *
     * @return List of currency info
     */
    @GetMapping("?periodicity=0")
    List<CurrencyResponse> getCurrencies();
}