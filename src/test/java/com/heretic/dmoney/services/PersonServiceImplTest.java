package com.heretic.dmoney.services;

import com.heretic.dmoney.arguments.persons.PersonGetArguments;
import com.heretic.dmoney.arguments.persons.PersonInvalidUpdateArguments;
import com.heretic.dmoney.arguments.persons.PersonSaveArguments;
import com.heretic.dmoney.dto.requests.PersonRequest;
import com.heretic.dmoney.dto.responses.PersonResponse;
import com.heretic.dmoney.entities.Person;
import com.heretic.dmoney.mappers.PersonMapperImpl;
import com.heretic.dmoney.repositories.PersonRepository;
import com.heretic.dmoney.services.impl.PersonServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.heretic.dmoney.util.Constants.ENTITY_NOT_FOUND_BY_ID;
import static com.heretic.dmoney.util.Constants.USER_NOT_FOUND_BY_USERNAME;
import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("PersonService tests")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestInstance(PER_CLASS)
class PersonServiceImplTest {

    @InjectMocks
    private PersonServiceImpl personService;
    @Autowired
    private PersonMapperImpl personMapper;
    @Mock
    private PersonRepository personRepository;
    private static final UUID ID = randomUUID();

    @BeforeEach
    public void init() {
        personService = new PersonServiceImpl(personMapper, personRepository);
    }

    @DisplayName("save person")
    @ParameterizedTest()
    @ArgumentsSource(PersonSaveArguments.class)
    public void savePersonTest(PersonRequest request, Person person, PersonResponse response) {
        when(personRepository.save(person)).thenReturn(person);

        PersonResponse actual = personService.savePerson(request);
        assertThat(actual).isEqualTo(response);

        verify(personRepository).save(person);
    }

    @DisplayName("get by valid id")
    @ParameterizedTest()
    @ArgumentsSource(PersonGetArguments.class)
    public void getPersonByValidIdTest(Person person, PersonResponse response) {
        when(personRepository.findById(ID)).thenReturn(of(person));

        PersonResponse actual = personService.getPerson(ID);
        assertThat(actual).isEqualTo(response);
        verify(personRepository).findById(ID);
    }

    @DisplayName("get by invalid id")
    @Test
    public void getPersonByInvalidIdTest() {
        when(personRepository.findById(ID)).thenReturn(empty());

        assertThatThrownBy(() -> personService.getPerson(ID)).isInstanceOf(EntityNotFoundException.class)
                .hasMessage(format(ENTITY_NOT_FOUND_BY_ID, ID));

        verify(personRepository).findById(ID);
    }

    @DisplayName("get by valid username")
    @ParameterizedTest()
    @ArgumentsSource(PersonGetArguments.class)
    public void getPersonByValidUsernameTest(Person person, PersonResponse response) {
        when(personRepository.findByUsername(anyString())).thenReturn(of(person));

        PersonResponse actual = personService.getPerson(anyString());
        assertThat(actual).isEqualTo(response);

        verify(personRepository).findByUsername(anyString());
    }

    @DisplayName("get by invalid username")
    @Test
    public void getPersonByInvalidUsernameTest() {
        when(personRepository.findByUsername(anyString())).thenReturn(empty());

        assertThatThrownBy(() -> personService.getPerson(anyString())).isInstanceOf(EntityNotFoundException.class)
                .hasMessage(format(USER_NOT_FOUND_BY_USERNAME, ""));

        verify(personRepository).findByUsername(anyString());
    }

    @DisplayName("get all persons")
    @ParameterizedTest()
    @ArgumentsSource(PersonGetArguments.class)
    public void getPersonsTest(Person person, PersonResponse response) {
        when(personRepository.findAll()).thenReturn(List.of(person));

        List<PersonResponse> actual = personService.getPersons();
        assertThat(actual).isEqualTo(List.of(response));

        verify(personRepository).findAll();
    }

    @DisplayName("update by valid ID")
    @ParameterizedTest()
    @ArgumentsSource(PersonSaveArguments.class)
    public void updatePersonByValidIdTest(PersonRequest request, Person person, PersonResponse response) {
        when(personRepository.findById(ID)).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(person);

        PersonResponse actual = personService.updatePerson(request, ID);
        assertThat(actual).isEqualTo(response);

        verify(personRepository).findById(ID);
        verify(personRepository).save(person);
    }

    @DisplayName("update by invalid ID")
    @ParameterizedTest()
    @ArgumentsSource(PersonInvalidUpdateArguments.class)
    public void updatePersonByInvalidIdTest(PersonRequest request) {
        when(personRepository.findById(ID)).thenReturn(empty());

        assertThatThrownBy(() -> personService.updatePerson(request, ID)).isInstanceOf(EntityNotFoundException.class)
                .hasMessage(format(ENTITY_NOT_FOUND_BY_ID, ID));
        verify(personRepository).findById(ID);
    }

    @DisplayName("delete person")
    @Test
    public void deletePersonTest() {
        assertThat(personService.deletePerson(ID)).isTrue();
    }
}