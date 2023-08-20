package com.heretic.dmoney.util;

import com.heretic.dmoney.dto.requests.PersonRequest;
import com.heretic.dmoney.entities.Person;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;
import java.util.HashSet;

import static com.heretic.dmoney.enums.UserRole.VISITOR;
import static com.heretic.dmoney.enums.UserStatus.ACTIVE;
import static java.time.LocalDate.of;
import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;

public final class Constants {

    public static final CurrencyUnit BYN = Monetary.getCurrency("BYN");
    public static final BigDecimal START_AMOUNT = BigDecimal.valueOf(0L);

    /**
     * Exception messages
     */
    public static final String ENTITY_NOT_FOUND_BY_ID_MASSAGE = "Nothing found by id %s";
    public static final String USER_NOT_FOUND_BY_USERNAME_MASSAGE = "User not found by username %s";

    /**
     * Objects for tests
     */
    public static final Person TEST_PERSON_ENTITY = Person.builder()
            .personId(randomUUID())
            .username("Test User")
            .password("12345")
            .email("test@test.com")
            .status(ACTIVE)
            .role(VISITOR)
            .birthday(of(2000, 1, 1))
            .registrationTime(now())
            .wallets(new HashSet<>())
            .build();

    public static final PersonRequest TEST_PERSON_REQUEST = PersonRequest.builder()
            .username("Test User")
            .password("12345")
            .email("test@test.com")
            .status(ACTIVE)
            .role(VISITOR)
            .birthday(of(2000, 1, 1))
            .registrationTime(now())
            .wallets(new HashSet<>())
            .build();

    private Constants() {
    }
}