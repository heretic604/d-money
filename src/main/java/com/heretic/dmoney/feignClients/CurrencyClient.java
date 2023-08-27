package com.heretic.dmoney.feignClients;

import com.heretic.dmoney.dto.responses.CurrencyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "currencyClient", url = "${client.currency.url}")
public interface CurrencyClient {

    @GetMapping("/{currency}?parammode=2")
    CurrencyResponse getCurrency(@PathVariable String currency);

    @GetMapping("?periodicity=0")
    List<CurrencyResponse> getCurrencies();
}