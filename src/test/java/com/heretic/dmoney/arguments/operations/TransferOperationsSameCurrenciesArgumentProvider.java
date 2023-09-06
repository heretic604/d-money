package com.heretic.dmoney.arguments.operations;

import com.heretic.dmoney.dto.requests.OperationRequest;
import com.heretic.dmoney.dto.responses.OperationResponse;
import com.heretic.dmoney.entities.Operation;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.UUID;
import java.util.stream.Stream;

import static com.heretic.dmoney.enums.OperationStatus.PROCESSED;
import static com.heretic.dmoney.enums.OperationStatus.SUCCEED;
import static com.heretic.dmoney.util.Constants.DEFAULT_CURRENCY;
import static java.math.BigDecimal.*;
import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;

public class TransferOperationsSameCurrenciesArgumentProvider implements ArgumentsProvider {

    private final static UUID OPERATION_ID = randomUUID();
//    private final static Wallet wallet = Wallet.builder()
//            .walletId(randomUUID())
//            .walletNumber(1L)
//            .currency(DEFAULT_CURRENCY)
//            .build();
    private final static OperationRequest DEPOSIT_OPERATION_REQUEST = OperationRequest.builder()
            .receiverNumber(1L)
            .senderNumber(1L)
            .amountIn(null)
            .currencyIn(null)
            .amountOut(TEN)
            .currencyOut(null)
            .status(PROCESSED)
            .time(now())
            .build();
    private final static Operation DEPOSIT_OPERATION = Operation.builder()
            .operationId(OPERATION_ID)
//            .receiver(wallet)
//            .sender(wallet)
            .amountIn(null)
            .currencyIn(null)
            .amountOut(TEN)
            .currencyOut(null)
            .exRate(null)
            .status(PROCESSED)
            .time(now())
            .build();
    private final static OperationResponse DEPOSIT_OPERATION_RESPONSE = OperationResponse.builder()
            .operationId(OPERATION_ID)
            .receiverNumber(1L)
            .senderNumber(1L)
            .amountIn(TEN)
            .currencyIn(DEFAULT_CURRENCY)
            .amountOut(TEN)
            .currencyOut(DEFAULT_CURRENCY)
            .exRate(ONE)
            .status(SUCCEED)
            .time(now())
            .build();

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(DEPOSIT_OPERATION_REQUEST, DEPOSIT_OPERATION, DEPOSIT_OPERATION_RESPONSE)
        );
    }
}