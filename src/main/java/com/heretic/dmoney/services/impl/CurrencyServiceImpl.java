package com.heretic.dmoney.services.impl;

import com.heretic.dmoney.dto.responses.CurrencyResponse;
import com.heretic.dmoney.feignClients.CurrencyClient;
import com.heretic.dmoney.services.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.heretic.dmoney.util.Constants.CURRENCY_PARAMMODE;
import static com.heretic.dmoney.util.Constants.CURRENCY_PERIODICITY;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyClient currencyClient;

    @Override
    @Transactional(readOnly = true)
    public CurrencyResponse getCurrency(String currency) {
        return currencyClient.getCurrency(currency, CURRENCY_PARAMMODE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CurrencyResponse> getCurrencies() {
        return currencyClient.getCurrencies(CURRENCY_PERIODICITY);
    }
}