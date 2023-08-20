package com.heretic.dmoney.services.impl;

import com.heretic.dmoney.dto.requests.PersonRequest;
import com.heretic.dmoney.dto.responses.PersonResponse;
import com.heretic.dmoney.repositories.PersonRepository;
import com.heretic.dmoney.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static com.heretic.dmoney.mappers.PersonMapper.INSTANCE;
import static com.heretic.dmoney.util.Constants.ENTITY_NOT_FOUND_BY_ID_MASSAGE;
import static com.heretic.dmoney.util.Constants.USER_NOT_FOUND_BY_USERNAME_MASSAGE;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public PersonResponse savePerson(PersonRequest personRequest) {
        return INSTANCE.toDto(personRepository.save(INSTANCE.toEntity(personRequest)));
    }

    @Override
    public PersonResponse getPerson(UUID id) {
        return personRepository.findById(id)
                .map(INSTANCE::toDto)
                .orElseThrow(() -> new EntityNotFoundException(format(ENTITY_NOT_FOUND_BY_ID_MASSAGE, id)));
    }

    @Override
    public PersonResponse getPerson(String username) {
        return personRepository.findByUsername(username)
                .map(INSTANCE::toDto)
                .orElseThrow(() -> new EntityNotFoundException(format(USER_NOT_FOUND_BY_USERNAME_MASSAGE, username)));
    }

    @Override
    public List<PersonResponse> getPersons() {
        return personRepository.findAll()
                .stream()
                .map(INSTANCE::toDto)
                .toList();
    }

    @Override
    public PersonResponse updatePerson(PersonRequest personRequest, UUID id) {
        return personRepository.updatePersonByPersonId(INSTANCE.toEntity(personRequest), id)
                .map(INSTANCE::toDto)
                .orElseThrow(() -> new EntityNotFoundException(format(ENTITY_NOT_FOUND_BY_ID_MASSAGE, id)));
    }

    @Override
    public boolean deletePerson(UUID id) {
        try {
            personRepository.deleteById(id);
            return true;
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}