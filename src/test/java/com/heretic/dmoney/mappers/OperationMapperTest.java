package com.heretic.dmoney.mappers;

import com.heretic.dmoney.dto.requests.OperationRequest;
import com.heretic.dmoney.dto.responses.OperationResponse;
import com.heretic.dmoney.entities.Operation;
import com.heretic.dmoney.entities.Wallet;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.heretic.dmoney.util.Constants.BYN;
import static com.heretic.dmoney.util.Constants.START_AMOUNT;
import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;
import static javax.money.convert.MonetaryConversions.getExchangeRateProvider;
import static org.assertj.core.api.Assertions.assertThat;

class OperationMapperTest {

    private static Operation entity;
    private static OperationRequest request;

    @BeforeAll
    static void init() {

        entity = Operation.builder()
                .operationId(randomUUID())
                .sender(Wallet.builder().build())
                .receiver(Wallet.builder().build())
                .currencyOut(BYN)
                .currencyIn(BYN)
                .amountOut(Money.of(START_AMOUNT, BYN))
                .amountIn(Money.of(START_AMOUNT, BYN))
                .exchangeRate(getExchangeRateProvider("ECB").getExchangeRate(BYN, BYN))
                .time(now())
                .build();

        request = OperationRequest.builder()
                .sender(Wallet.builder().build())
                .receiver(Wallet.builder().build())
                .currencyOut(BYN)
                .currencyIn(BYN)
                .amountOut(Money.of(START_AMOUNT, BYN))
                .amountIn(Money.of(START_AMOUNT, BYN))
                .exchangeRate(getExchangeRateProvider("ECB").getExchangeRate(BYN, BYN))
                .time(now())
                .build();

    }

    @Test
    void toEntity() {
        Operation actual = OperationMapper.INSTANCE.toEntity(request);
        assertThat(actual).isNotNull();
        assertThat(actual.getSender()).isEqualTo(request.getSender());
        assertThat(actual.getReceiver()).isEqualTo(request.getReceiver());
        assertThat(actual.getCurrencyOut()).isEqualTo(request.getCurrencyOut());
        assertThat(actual.getCurrencyIn()).isEqualTo(request.getCurrencyIn());
        assertThat(actual.getAmountOut()).isEqualTo(request.getAmountOut());
        assertThat(actual.getAmountIn()).isEqualTo(request.getAmountOut());
        assertThat(actual.getExchangeRate()).isEqualTo(request.getExchangeRate());
        assertThat(actual.getTime()).isEqualTo(request.getTime());
    }

    @Test
    void toDto() {
        OperationResponse actual = OperationMapper.INSTANCE.toDto(entity);
        assertThat(actual).isNotNull();
        assertThat(actual.getOperationId()).isEqualTo(entity.getOperationId());
        assertThat(actual.getSender()).isEqualTo(entity.getSender());
        assertThat(actual.getReceiver()).isEqualTo(entity.getReceiver());
        assertThat(actual.getCurrencyOut()).isEqualTo(entity.getCurrencyOut());
        assertThat(actual.getCurrencyIn()).isEqualTo(entity.getCurrencyIn());
        assertThat(actual.getAmountOut()).isEqualTo(entity.getAmountOut());
        assertThat(actual.getAmountIn()).isEqualTo(entity.getAmountOut());
        assertThat(actual.getExchangeRate()).isEqualTo(entity.getExchangeRate());
        assertThat(actual.getTime()).isEqualTo(entity.getTime());
    }
}