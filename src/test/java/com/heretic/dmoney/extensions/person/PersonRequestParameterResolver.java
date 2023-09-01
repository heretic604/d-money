package com.heretic.dmoney.extensions.person;

import com.heretic.dmoney.dto.requests.PersonRequest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.time.LocalDate;

import static com.heretic.dmoney.dto.requests.PersonRequest.builder;
import static com.heretic.dmoney.enums.UserRole.VISITOR;
import static com.heretic.dmoney.enums.UserStatus.ACTIVE;
import static java.time.LocalDateTime.of;

public class PersonRequestParameterResolver implements ParameterResolver {

    public static final PersonRequest TEST_PERSON_REQUEST = builder()
            .username("test")
            .password("123")
            .email("test@test.by")
            .status(ACTIVE)
            .role(VISITOR)
            .birthday(LocalDate.of(2000, 1, 1))
            .registrationTime(of(2020, 2, 2, 16, 0))
            .build();

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == PersonRequest.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return TEST_PERSON_REQUEST;
    }
}