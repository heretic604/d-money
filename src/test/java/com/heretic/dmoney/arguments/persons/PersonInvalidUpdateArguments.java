package com.heretic.dmoney.arguments.persons;

import com.heretic.dmoney.dto.requests.PersonRequest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static com.heretic.dmoney.enums.UserRole.VISITOR;
import static com.heretic.dmoney.enums.UserStatus.ACTIVE;
import static java.time.LocalDate.of;
import static java.time.LocalDateTime.of;

public class PersonInvalidUpdateArguments implements ArgumentsProvider {

    private static final PersonRequest TEST_PERSON_REQUEST = PersonRequest.builder()
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
                Arguments.of(TEST_PERSON_REQUEST)
        );
    }
}