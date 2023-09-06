package com.heretic.dmoney.arguments;

import com.heretic.dmoney.dto.requests.WalletRequest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import static com.heretic.dmoney.dto.requests.WalletRequest.builder;
import static com.heretic.dmoney.util.Constants.DEFAULT_CURRENCY;
import static java.math.BigDecimal.ZERO;

public class WalletRequestParameterResolver implements ParameterResolver {

    public static final WalletRequest TEST_WALLET_REQUEST = builder()
            .walletNumber(1L)
            .currency(DEFAULT_CURRENCY)
            .amount(ZERO)
            .build();

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == WalletRequest.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return TEST_WALLET_REQUEST;
    }
}