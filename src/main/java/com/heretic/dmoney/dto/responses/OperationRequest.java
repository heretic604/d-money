package com.heretic.dmoney.dto.responses;

import com.heretic.dmoney.entities.Wallet;
import lombok.Builder;
import lombok.Data;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.convert.ExchangeRate;
import java.time.LocalDateTime;

@Data
@Builder
public class OperationRequest {

    private Wallet sender;
    private Wallet receiver;
    private CurrencyUnit currencyOut;
    private CurrencyUnit currencyIn;
    private MonetaryAmount amountOut;
    private MonetaryAmount amountIn;
    private ExchangeRate exchangeRate;
    private LocalDateTime time;
}