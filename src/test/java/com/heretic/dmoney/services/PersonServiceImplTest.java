package com.heretic.dmoney.services;

import com.heretic.dmoney.dto.requests.PersonRequest;
import com.heretic.dmoney.dto.responses.PersonResponse;
import com.heretic.dmoney.entities.Person;
import com.heretic.dmoney.extensions.PersonRequestParameterResolver;
import com.heretic.dmoney.mappers.PersonMapper;
import com.heretic.dmoney.mappers.PersonMapperImpl;
import com.heretic.dmoney.repositories.PersonRepository;
import com.heretic.dmoney.services.impl.PersonServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("PersonService tests")
@ExtendWith({MockitoExtension.class, PersonRequestParameterResolver.class})
class PersonServiceImplTest {

    @InjectMocks
    private PersonServiceImpl personService;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private PersonMapper personMapper;
    private static UUID personId;
    private static Person person;
    private static PersonResponse personResponse;

    @BeforeAll
    public static void init(PersonRequest personRequest) {
        personId = randomUUID();
        PersonMapperImpl mapper = new PersonMapperImpl();
        person = mapper.mapToPerson(personRequest);
        person.setPersonId(personId);
        personResponse = mapper.mapToPersonResponse(person);
    }

    @Test()
    public void savePersonTest(PersonRequest personRequest) {
        when(personRepository.save(person)).thenReturn(person);
        when(personMapper.mapToPerson(personRequest)).thenReturn(person);
        when(personMapper.mapToPersonResponse(person)).thenReturn(personResponse);

        assertEquals(personResponse, personService.savePerson(personRequest));
        verify(personRepository, times(1)).save(person);
        verify(personMapper, times(1)).mapToPersonResponse(person);
        verify(personMapper, times(1)).mapToPerson(personRequest);
    }

    @Test()
    public void getPersonByValidIdTest() {
        when(personMapper.mapToPersonResponse(person)).thenReturn(personResponse);
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));

        assertEquals(personId, personService.getPerson(personId).getPersonId());
        verify(personMapper, times(1)).mapToPersonResponse(person);
        verify(personRepository, times(1)).findById(personId);
    }

    @Test()
    public void getPersonByInvalidIdTest() {
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> personService.getPerson(personId));
        verify(personRepository, times(1)).findById(personId);
    }

    @Test
    public void getPersonByValidUsernameTest(PersonRequest personRequest) {
        String validUsername = personRequest.getUsername();
        when(personMapper.mapToPersonResponse(person)).thenReturn(personResponse);
        when(personRepository.findByUsername(validUsername)).thenReturn(Optional.of(person));

        assertEquals(validUsername, personService.getPerson(validUsername).getUsername());
        verify(personMapper, times(1)).mapToPersonResponse(person);
        verify(personRepository, times(1)).findByUsername(validUsername);
    }

    @Test
    public void getPersonByInvalidUsernameTest() {
        String invalidUsername = "invalid_username";
        when(personRepository.findByUsername(invalidUsername)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> personService.getPerson(invalidUsername));
        verify(personRepository, times(1)).findByUsername(invalidUsername);
    }

    @Test
    public void getPersonsTest() {
        when(personMapper.mapToPersonResponse(person)).thenReturn(personResponse);
        when(personRepository.findAll()).thenReturn(List.of(person));

        assertEquals(List.of(personResponse), personService.getPersons());
        verify(personMapper, times(1)).mapToPersonResponse(person);
        verify(personRepository, times(1)).findAll();
    }

    @Test
    public void updatePersonByValidIdTest(PersonRequest personRequest) {
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(personMapper.updatePerson(personRequest, person)).thenReturn(person);
        when(personRepository.save(person)).thenReturn(person);
        when(personMapper.mapToPersonResponse(person)).thenReturn(personResponse);

        assertEquals(personResponse, personService.updatePerson(personRequest, personId));
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(1)).save(person);
        verify(personMapper, times(1)).updatePerson(personRequest, person);
        verify(personMapper, times(1)).mapToPersonResponse(person);
    }

    @Test
    public void updatePersonByInvalidIdTest(PersonRequest personRequest) {
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> personService.updatePerson(personRequest, personId));
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(0)).save(person);
        verify(personMapper, times(0)).updatePerson(personRequest, person);
        verify(personMapper, times(0)).mapToPersonResponse(person);
    }

    @Test
    public void deletePersonTest() {
        assertTrue(personService.deletePerson(personId));
    }
}