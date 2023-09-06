package com.heretic.dmoney.arguments.operations;

import com.heretic.dmoney.dto.requests.OperationRequest;
import com.heretic.dmoney.dto.responses.OperationResponse;
import com.heretic.dmoney.dto.responses.WalletResponse;
import com.heretic.dmoney.entities.Operation;
import com.heretic.dmoney.entities.Wallet;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static com.heretic.dmoney.enums.OperationStatus.PROCESSED;
import static com.heretic.dmoney.enums.OperationStatus.SUCCEED;
import static com.heretic.dmoney.util.Constants.DEFAULT_CURRENCY;
import static java.math.BigDecimal.*;
import static java.time.LocalDateTime.of;

public class SaveTransferOperationInsufficientArguments implements ArgumentsProvider {

    private final static WalletResponse WALLET_RESPONSE = WalletResponse.builder()
            .walletNumber(1L)
            .amount(ZERO)
            .currency(DEFAULT_CURRENCY)
            .build();
    private final static OperationRequest TRANSFER_OPERATION_REQUEST = OperationRequest.builder()
            .receiverNumber(1L)
            .senderNumber(1L)
            .amountIn(null)
            .currencyIn(null)
            .amountOut(TEN)
            .currencyOut(null)
            .status(PROCESSED)
            .time(of(2023, 1, 1, 16, 0))
            .build();

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(Arguments.of(TRANSFER_OPERATION_REQUEST, WALLET_RESPONSE));
    }
}