package com.heretic.dmoney.dto.requests;

import com.heretic.dmoney.entities.Wallet;
import lombok.Builder;
import lombok.Data;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.convert.ExchangeRate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class OperationResponse {

    private UUID operationId;
    private Wallet sender;
    private Wallet receiver;
    private CurrencyUnit currencyOut;
    private CurrencyUnit currencyIn;
    private MonetaryAmount amountOut;
    private MonetaryAmount amountIn;
    private ExchangeRate exchangeRate;
    private LocalDateTime time;
}