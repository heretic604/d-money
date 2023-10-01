package com.heretic.dmoney.services.impl;

import com.heretic.dmoney.dto.requests.PersonRequest;
import com.heretic.dmoney.dto.responses.PersonResponse;
import com.heretic.dmoney.entities.Person;
import com.heretic.dmoney.mappers.PersonMapper;
import com.heretic.dmoney.repositories.PersonRepository;
import com.heretic.dmoney.services.PersonService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.heretic.dmoney.util.Constants.ENTITY_NOT_FOUND_BY_ID;
import static com.heretic.dmoney.util.Constants.USER_NOT_FOUND_BY_USERNAME;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final CheckGenerationPasswordImpl passwordService;
    private final PersonMapper mapper;
    private final PersonRepository personRepository;

    @Override
    @Transactional
    public PersonResponse savePerson(PersonRequest personRequest) {
//        Person savedPerson = personRepository.save(mapper.mapToPerson(personRequest));
        Person person = mapper.mapToPerson(personRequest);
        person.setPassword(passwordService.getHashPassword(personRequest.getPassword()));
        person = personRepository.save(person);
        return mapper.mapToPersonResponse(person);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonResponse getPerson(UUID id) {
        return personRepository.findById(id)
                .map(mapper::mapToPersonResponse)
                .orElseThrow(() -> new EntityNotFoundException(format(ENTITY_NOT_FOUND_BY_ID, id)));
    }

    @Override
    @Transactional(readOnly = true)
    public PersonResponse getPerson(String username) {
        return personRepository.findByUsername(username)
                .map(mapper::mapToPersonResponse)
                .orElseThrow(() -> new EntityNotFoundException(format(USER_NOT_FOUND_BY_USERNAME, username)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonResponse> getPersons() {
        return personRepository.findAll()
                .stream()
                .map(mapper::mapToPersonResponse)
                .toList();
    }

    @Override
    @Transactional
    public PersonResponse updatePerson(PersonRequest personRequest, UUID id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(ENTITY_NOT_FOUND_BY_ID, id)));
        Person savedPerson = personRepository.save(mapper.updatePerson(personRequest, person));
        return mapper.mapToPersonResponse(savedPerson);
    }

    @Override
    @Transactional
    public boolean deletePerson(UUID id) {
        personRepository.deleteById(id);
        return true;
    }
}