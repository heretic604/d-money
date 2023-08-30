package com.heretic.dmoney.controllers;

import com.heretic.dmoney.dto.responses.CurrencyResponse;
import com.heretic.dmoney.services.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping(value = "/currency/{currency}")
    public CurrencyResponse getCurrency(@PathVariable String currency) {
        return currencyService.getCurrency(currency);
    }

    @GetMapping("/currencies")
    public List<CurrencyResponse> getCurrencies() {
        return currencyService.getCurrencies();
    }
}