package com.heretic.dmoney.services;

import com.heretic.dmoney.dto.responses.CurrencyResponse;

import java.util.List;

/**
 * Service processes currency data received from a feign client
 */
public interface CurrencyService {

    /**
     * Get currency data from feign client
     *
     * @param currency Currency name (USD, EUR etc.)
     * @return currency data
     */
    CurrencyResponse getCurrency(String currency);

    /**
     * Get all available currencies at nbrb.by
     *
     * @return List of currencies
     */
    List<CurrencyResponse> getCurrencies();
}