package com.heretic.dmoney.arguments.wallets;

import com.heretic.dmoney.dto.requests.WalletRequest;
import com.heretic.dmoney.dto.responses.PersonResponse;
import com.heretic.dmoney.dto.responses.WalletResponse;
import com.heretic.dmoney.entities.Person;
import com.heretic.dmoney.entities.Wallet;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static com.heretic.dmoney.enums.UserRole.VISITOR;
import static com.heretic.dmoney.enums.UserStatus.ACTIVE;
import static com.heretic.dmoney.util.Constants.DEFAULT_CURRENCY;
import static java.math.BigDecimal.TEN;
import static java.time.LocalDate.of;
import static java.time.LocalDateTime.of;

public class WalletSaveArguments implements ArgumentsProvider {

    private static final Person PERSON = Person.builder()
            .username("test")
            .password("123")
            .email("test@test.by")
            .status(ACTIVE)
            .role(VISITOR)
            .birthday(of(2000, 1, 1))
            .registrationTime(of(2020, 2, 2, 16, 0))
            .build();
    private static final WalletRequest WALLET_REQUEST = WalletRequest.builder()
            .walletNumber(1L)
            .currency(DEFAULT_CURRENCY)
            .amount(TEN)
            .build();
    private static final Wallet WALLET = Wallet.builder()
            .walletNumber(1L)
            .currency(DEFAULT_CURRENCY)
            .amount(TEN)
            .person(PERSON)
            .build();
    private static final WalletResponse WALLET_RESPONSE = WalletResponse.builder()
            .walletNumber(1L)
            .currency(DEFAULT_CURRENCY)
            .amount(TEN)
            .build();
    private static final PersonResponse PERSON_RESPONSE = PersonResponse.builder()
            .username("test")
            .password("123")
            .email("test@test.by")
            .status(ACTIVE)
            .role(VISITOR)
            .birthday(of(2000, 1, 1))
            .registrationTime(of(2020, 2, 2, 16, 0))
            .build();

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(WALLET_REQUEST, WALLET, WALLET_RESPONSE, PERSON_RESPONSE)
        );
    }
}