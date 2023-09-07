package com.heretic.dmoney.feignClients;

import com.heretic.dmoney.dto.responses.CurrencyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Receives currency data from the National Bank of the Republic of Belarus (nbrb.by)
 */
@FeignClient(value = "currencyClient", url = "${client.currency.url}")
public interface CurrencyClient {

    /**
     * Get currency data
     *
     * @param currency currency name (RUB, EUR, AUD etc.)
     * @return currency info
     */
    @GetMapping("/{currency}")
    CurrencyResponse getCurrency(@PathVariable String currency, @RequestParam int parammode);


    /**
     * Get all available currency data
     *
     * @return List of currency info
     */
    @GetMapping()
    List<CurrencyResponse> getCurrencies(@RequestParam int periodicity);
}