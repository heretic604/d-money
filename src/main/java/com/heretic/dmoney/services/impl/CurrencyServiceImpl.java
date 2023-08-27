package com.heretic.dmoney.services.impl;

import com.heretic.dmoney.dto.responses.CurrencyResponse;
import com.heretic.dmoney.feignClients.CurrencyClient;
import com.heretic.dmoney.services.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyClient currencyClient;

    @Override
    public CurrencyResponse getCurrency(String currency) {
        return currencyClient.getCurrency(currency);
    }

    @Override
    public List<CurrencyResponse> getAllCurrencies() {
        return currencyClient.getCurrencies();
    }
}