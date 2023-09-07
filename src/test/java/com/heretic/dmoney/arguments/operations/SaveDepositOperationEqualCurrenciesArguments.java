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
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.time.LocalDateTime.of;

public class SaveDepositOperationEqualCurrenciesArguments implements ArgumentsProvider {

    private final static WalletResponse WALLET_RESPONSE = WalletResponse.builder()
            .walletNumber(1L)
            .amount(TEN)
            .currency(DEFAULT_CURRENCY)
            .build();
    private final static Wallet WALLET = Wallet.builder()
            .walletNumber(1L)
            .amount(TEN)
            .currency(DEFAULT_CURRENCY)
            .build();
    private final static OperationRequest DEPOSIT_OPERATION_REQUEST = OperationRequest.builder()
            .receiverNumber(1L)
            .senderNumber(null)
            .amountIn(null)
            .currencyIn(null)
            .amountOut(TEN)
            .currencyOut(DEFAULT_CURRENCY)
            .status(PROCESSED)
            .time(of(2023, 1, 1, 16, 0))
            .build();
    private final static Operation DEPOSIT_OPERATION = Operation.builder()
            .receiver(WALLET)
            .sender(null)
            .amountIn(null)
            .currencyIn(null)
            .amountOut(TEN)
            .currencyOut(DEFAULT_CURRENCY)
            .exRate(ONE)
            .amountIn(TEN)
            .status(SUCCEED)
            .time(of(2023, 1, 1, 16, 0))
            .build();
    private final static OperationResponse DEPOSIT_OPERATION_RESPONSE = OperationResponse.builder()
            .receiverNumber(1L)
            .senderNumber(null)
            .amountIn(TEN)
            .currencyIn(DEFAULT_CURRENCY)
            .amountOut(TEN)
            .currencyOut(DEFAULT_CURRENCY)
            .exRate(ONE)
            .status(SUCCEED)
            .time(of(2023, 1, 1, 16, 0))
            .build();

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(
                        DEPOSIT_OPERATION_REQUEST,
                        DEPOSIT_OPERATION,
                        DEPOSIT_OPERATION_RESPONSE,
                        WALLET_RESPONSE)
        );
    }
}