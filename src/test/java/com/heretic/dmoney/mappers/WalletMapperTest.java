package com.heretic.dmoney.mappers;

import com.heretic.dmoney.dto.requests.WalletRequest;
import com.heretic.dmoney.dto.responses.WalletResponse;
import com.heretic.dmoney.entities.Person;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static com.heretic.dmoney.util.Constants.BYN;
import static com.heretic.dmoney.util.Constants.START_AMOUNT;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

class WalletMapperTest {

    private static Wallet entity;
    private static WalletRequest request;

    @BeforeAll
    static void init() {

        entity = Wallet.builder()
                .walletId(randomUUID())
                .walletNumber("001")
                .person(Person.builder().build())
                .currency(BYN)
                .amount(Money.of(START_AMOUNT, BYN))
                .incomeOperations(new HashSet<>())
                .outcomeOperations(new HashSet<>())
                .build();

        request = WalletRequest.builder()
                .walletNumber("001")
                .person(Person.builder().build())
                .currency(BYN)
                .amount(Money.of(START_AMOUNT, BYN))
                .incomeOperations(new HashSet<>())
                .outcomeOperations(new HashSet<>())
                .build();
    }

    @Test
    void toEntityTest() {
        Wallet actual = WalletMapper.INSTANCE.toEntity(request);
        assertThat(actual).isNotNull();
        assertThat(actual.getWalletNumber()).isEqualTo(request.getWalletNumber());
        assertThat(actual.getPerson()).isEqualTo(request.getPerson());
        assertThat(actual.getCurrency()).isEqualTo(request.getCurrency());
        assertThat(actual.getAmount()).isEqualTo(request.getAmount());
        assertThat(actual.getIncomeOperations()).isEqualTo(request.getIncomeOperations());
        assertThat(actual.getOutcomeOperations()).isEqualTo(request.getOutcomeOperations());
    }

    @Test
    void toDtoTest() {
        WalletResponse actual = WalletMapper.INSTANCE.toDto(entity);
        assertThat(actual).isNotNull();
        assertThat(actual.getWalletNumber()).isEqualTo(entity.getWalletNumber());
        assertThat(actual.getPerson()).isEqualTo(entity.getPerson());
        assertThat(actual.getCurrency()).isEqualTo(entity.getCurrency());
        assertThat(actual.getAmount()).isEqualTo(entity.getAmount());
        assertThat(actual.getIncomeOperations()).isEqualTo(entity.getIncomeOperations());
        assertThat(actual.getIncomeOperations()).isEqualTo(entity.getIncomeOperations());
    }
}