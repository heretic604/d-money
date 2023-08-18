package com.heretic.dmoney.mappers;

import com.heretic.dmoney.dto.requests.PersonRequest;
import com.heretic.dmoney.dto.responses.PersonResponse;
import com.heretic.dmoney.entities.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static com.heretic.dmoney.enums.UserRole.VISITOR;
import static com.heretic.dmoney.enums.UserStatus.ACTIVE;
import static java.time.LocalDate.of;
import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

class PersonMapperTest {

    public static Person entity;
    public static PersonRequest request;

    @BeforeAll
    static void init() {

        entity = Person.builder()
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

        request = PersonRequest.builder()
                .username("Test User")
                .password("12345")
                .email("test@test.com")
                .status(ACTIVE)
                .role(VISITOR)
                .birthday(of(2000, 1, 1))
                .registrationTime(now())
                .wallets(new HashSet<>())
                .build();
    }

    @Test
    void toEntityTest() {
        Person actual = PersonMapper.INSTANCE.toEntity(request);
        assertThat(actual).isNotNull();
        assertThat(actual.getUsername()).isEqualTo(request.getUsername());
        assertThat(actual.getPassword()).isEqualTo(request.getPassword());
        assertThat(actual.getEmail()).isEqualTo(request.getEmail());
        assertThat(actual.getStatus()).isEqualTo(request.getStatus());
        assertThat(actual.getRole()).isEqualTo(request.getRole());
        assertThat(actual.getBirthday()).isEqualTo(request.getBirthday());
        assertThat(actual.getRegistrationTime()).isEqualTo(request.getRegistrationTime());
        assertThat(actual.getWallets()).isEqualTo(request.getWallets());
    }

    @Test
    void toDtoTest() {
        PersonResponse actual = PersonMapper.INSTANCE.toDto(entity);
        assertThat(actual).isNotNull();
        assertThat(actual.getPersonId()).isEqualTo(entity.getPersonId());
        assertThat(actual.getUsername()).isEqualTo(entity.getUsername());
        assertThat(actual.getPassword()).isEqualTo(entity.getPassword());
        assertThat(actual.getEmail()).isEqualTo(entity.getEmail());
        assertThat(actual.getStatus()).isEqualTo(entity.getStatus());
        assertThat(actual.getRole()).isEqualTo(entity.getRole());
        assertThat(actual.getBirthday()).isEqualTo(entity.getBirthday());
        assertThat(actual.getRegistrationTime()).isEqualTo(entity.getRegistrationTime());
        assertThat(actual.getWallets()).isEqualTo(entity.getWallets());
    }
}