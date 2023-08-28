package com.heretic.dmoney.services.impl;

import com.heretic.dmoney.dto.requests.PersonRequest;
import com.heretic.dmoney.dto.responses.PersonResponse;
import com.heretic.dmoney.entities.Person;
import com.heretic.dmoney.mappers.EntityDtoMapper;
import com.heretic.dmoney.repositories.PersonRepository;
import com.heretic.dmoney.services.PersonService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.heretic.dmoney.util.Constants.ENTITY_NOT_FOUND_BY_ID;
import static com.heretic.dmoney.util.Constants.USER_NOT_FOUND_BY_USERNAME;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final EntityDtoMapper mapper;

    @Override
    public PersonResponse savePerson(PersonRequest personRequest) {
        return mapper.personEntityToDTO(personRepository.save(mapper.personDTOtoEntity(personRequest)));
    }

    @Override
    public PersonResponse getPerson(UUID id) {
        return personRepository.findById(id)
                .map(mapper::personEntityToDTO)
                .orElseThrow(() -> new EntityNotFoundException(format(ENTITY_NOT_FOUND_BY_ID, id)));
    }

    @Override
    public PersonResponse getPerson(String username) {
        return personRepository.findByUsername(username)
                .map(mapper::personEntityToDTO)
                .orElseThrow(() -> new EntityNotFoundException(format(USER_NOT_FOUND_BY_USERNAME, username)));
    }

    @Override
    public List<PersonResponse> getPersons() {
        return personRepository.findAll()
                .stream()
                .map(mapper::personEntityToDTO)
                .toList();
    }

    @Override
    public PersonResponse updatePerson(PersonRequest personRequest, UUID id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(ENTITY_NOT_FOUND_BY_ID, id)));
        return mapper.personEntityToDTO(personRepository.save(mapper.updatePersonEntity(personRequest, person)));
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