package com.heretic.dmoney.services;

import com.heretic.dmoney.dto.responses.CurrencyResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CurrencyService {

    CurrencyResponse getCurrency(String currency);

    List<CurrencyResponse> getAllCurrencies();
}