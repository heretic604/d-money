package com.heretic.dmoney.arguments.wallets;

import com.heretic.dmoney.dto.responses.WalletResponse;
import com.heretic.dmoney.entities.Wallet;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static com.heretic.dmoney.util.Constants.DEFAULT_CURRENCY;
import static java.math.BigDecimal.TEN;

public class WalletGetArguments implements ArgumentsProvider {

    private static final Wallet WALLET = Wallet.builder()
            .walletNumber(1L)
            .currency(DEFAULT_CURRENCY)
            .amount(TEN)
            .build();
    private static final WalletResponse WALLET_RESPONSE = WalletResponse.builder()
            .walletNumber(1L)
            .currency(DEFAULT_CURRENCY)
            .amount(TEN)
            .build();

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(WALLET, WALLET_RESPONSE)
        );
    }
}