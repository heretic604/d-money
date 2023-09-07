package com.heretic.dmoney.arguments.operations;

import com.heretic.dmoney.dto.requests.OperationRequest;
import com.heretic.dmoney.dto.responses.CurrencyResponse;
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
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDateTime.of;

public class SaveTransferDifferentCurrenciesArguments implements ArgumentsProvider {

    private final static WalletResponse WALLET_RESPONSE_BYN = WalletResponse.builder()
            .walletNumber(1L)
            .amount(valueOf(1000))
            .currency(DEFAULT_CURRENCY)
            .build();
    private final static WalletResponse WALLET_RESPONSE_USD = WalletResponse.builder()
            .walletNumber(2L)
            .amount(valueOf(1000))
            .currency("USD")
            .build();
    private final static Wallet WALLET_BYN = Wallet.builder()
            .walletNumber(1L)
            .amount(valueOf(1000))
            .currency(DEFAULT_CURRENCY)
            .build();
    private final static Wallet WALLET_USD = Wallet.builder()
            .walletNumber(2L)
            .amount(valueOf(1000))
            .currency("USD")
            .build();
    private final static OperationRequest TRANSFER_OPERATION_REQUEST_BYN_USD = OperationRequest.builder()
            .receiverNumber(2L)
            .senderNumber(1L)
            .amountIn(null)
            .currencyIn(null)
            .amountOut(valueOf(3))
            .currencyOut(null)
            .status(PROCESSED)
            .time(of(2023, 1, 1, 16, 0))
            .build();
    private final static Operation TRANSFER_OPERATION_BYN_USD = Operation.builder()
            .receiver(WALLET_USD)
            .sender(WALLET_BYN)
            .amountIn(valueOf(0.75))
            .currencyIn(null)
            .amountOut(valueOf(3))
            .currencyOut(null)
            .exRate(valueOf(0.25))
            .status(SUCCEED)
            .time(of(2023, 1, 1, 16, 0))
            .build();
    private final static OperationResponse TRANSFER_OPERATION_RESPONSE_BYN_USD = OperationResponse.builder()
            .receiverNumber(2L)
            .senderNumber(1L)
            .amountIn(valueOf(0.75))
            .currencyIn("USD")
            .amountOut(valueOf(3))
            .currencyOut(DEFAULT_CURRENCY)
            .exRate(valueOf(0.25))
            .status(SUCCEED)
            .time(of(2023, 1, 1, 16, 0))
            .build();
    private static final CurrencyResponse CURRENCY_RESPONSE_BYN_USD = CurrencyResponse.builder()
            .rate(valueOf(4))
            .build();
    private final static OperationRequest TRANSFER_OPERATION_REQUEST_USD_BYN = OperationRequest.builder()
            .receiverNumber(1L)
            .senderNumber(2L)
            .amountIn(null)
            .currencyIn(null)
            .amountOut(valueOf(3))
            .currencyOut(null)
            .status(PROCESSED)
            .time(of(2023, 1, 1, 16, 0))
            .build();
    private final static Operation TRANSFER_OPERATION_USD_BYN = Operation.builder()
            .receiver(WALLET_BYN)
            .sender(WALLET_USD)
            .amountIn(valueOf(12.75))
            .currencyIn(null)
            .amountOut(valueOf(3))
            .currencyOut(null)
            .exRate(valueOf(4.25))
            .status(SUCCEED)
            .time(of(2023, 1, 1, 16, 0))
            .build();
    private final static OperationResponse TRANSFER_OPERATION_RESPONSE_USD_BYN = OperationResponse.builder()
            .receiverNumber(1L)
            .senderNumber(2L)
            .amountIn(valueOf(12.75))
            .currencyIn(DEFAULT_CURRENCY)
            .amountOut(valueOf(3))
            .currencyOut("USD")
            .exRate(valueOf(4.25))
            .status(SUCCEED)
            .time(of(2023, 1, 1, 16, 0))
            .build();
    private static final CurrencyResponse CURRENCY_RESPONSE_USD_BYN = CurrencyResponse.builder()
            .rate(valueOf(4.25))
            .build();

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(
                        TRANSFER_OPERATION_REQUEST_BYN_USD,
                        TRANSFER_OPERATION_BYN_USD,
                        TRANSFER_OPERATION_RESPONSE_BYN_USD,
                        WALLET_RESPONSE_BYN,
                        WALLET_RESPONSE_USD,
                        CURRENCY_RESPONSE_BYN_USD),
                Arguments.of(
                        TRANSFER_OPERATION_REQUEST_USD_BYN,
                        TRANSFER_OPERATION_USD_BYN,
                        TRANSFER_OPERATION_RESPONSE_USD_BYN,
                        WALLET_RESPONSE_BYN,
                        WALLET_RESPONSE_USD,
                        CURRENCY_RESPONSE_USD_BYN)
        );
    }
}