package com.heretic.dmoney.extensions.operations;

import com.heretic.dmoney.entities.Wallet;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.math.BigDecimal;

import static com.heretic.dmoney.entities.Wallet.builder;
import static java.util.UUID.randomUUID;

public class WalletDataForOperationsParameterResolver implements ParameterResolver {

    public static final Wallet TEST_WALLET_DATA = builder()
            .walletId(randomUUID())
            .walletNumber(1L)
            .build();

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == Wallet.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return TEST_WALLET_DATA;
    }
}