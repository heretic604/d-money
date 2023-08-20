package com.heretic.dmoney.mappers;

import com.heretic.dmoney.dto.responses.PersonResponse;
import com.heretic.dmoney.entities.Person;
import org.junit.jupiter.api.Test;

import static com.heretic.dmoney.util.Constants.TEST_PERSON_ENTITY;
import static com.heretic.dmoney.util.Constants.TEST_PERSON_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;

class PersonMapperTest {

    @Test
    void toEntityTest() {
        Person actual = PersonMapper.INSTANCE.toEntity(TEST_PERSON_REQUEST);
        assertThat(actual).isNotNull();
        assertThat(actual.getUsername()).isEqualTo(TEST_PERSON_REQUEST.getUsername());
        assertThat(actual.getPassword()).isEqualTo(TEST_PERSON_REQUEST.getPassword());
        assertThat(actual.getEmail()).isEqualTo(TEST_PERSON_REQUEST.getEmail());
        assertThat(actual.getStatus()).isEqualTo(TEST_PERSON_REQUEST.getStatus());
        assertThat(actual.getRole()).isEqualTo(TEST_PERSON_REQUEST.getRole());
        assertThat(actual.getBirthday()).isEqualTo(TEST_PERSON_REQUEST.getBirthday());
        assertThat(actual.getRegistrationTime()).isEqualTo(TEST_PERSON_REQUEST.getRegistrationTime());
        assertThat(actual.getWallets()).isEqualTo(TEST_PERSON_REQUEST.getWallets());
    }

    @Test
    void toDtoTest() {
        PersonResponse actual = PersonMapper.INSTANCE.toDto(TEST_PERSON_ENTITY);
        assertThat(actual).isNotNull();
        assertThat(actual.getPersonId()).isEqualTo(TEST_PERSON_ENTITY.getPersonId());
        assertThat(actual.getUsername()).isEqualTo(TEST_PERSON_ENTITY.getUsername());
        assertThat(actual.getPassword()).isEqualTo(TEST_PERSON_ENTITY.getPassword());
        assertThat(actual.getEmail()).isEqualTo(TEST_PERSON_ENTITY.getEmail());
        assertThat(actual.getStatus()).isEqualTo(TEST_PERSON_ENTITY.getStatus());
        assertThat(actual.getRole()).isEqualTo(TEST_PERSON_ENTITY.getRole());
        assertThat(actual.getBirthday()).isEqualTo(TEST_PERSON_ENTITY.getBirthday());
        assertThat(actual.getRegistrationTime()).isEqualTo(TEST_PERSON_ENTITY.getRegistrationTime());
        assertThat(actual.getWallets()).isEqualTo(TEST_PERSON_ENTITY.getWallets());
    }
}