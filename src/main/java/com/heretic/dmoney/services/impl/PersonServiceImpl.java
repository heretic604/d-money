package com.heretic.dmoney.services.impl;

import com.heretic.dmoney.dto.responses.PersonResponse;
import com.heretic.dmoney.mappers.PersonMapper;
import com.heretic.dmoney.repositories.PersonRepository;
import com.heretic.dmoney.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static com.heretic.dmoney.util.Constants.ENTITY_NOT_FOUND_MASSAGE;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonMapper personMapper;
    private final PersonRepository personRepository;

    @Override
    public PersonResponse getPerson(UUID id) {
        return personRepository.findById(id)
                .map(personMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(format(ENTITY_NOT_FOUND_MASSAGE, id)));
    }

    @Override
    public List<PersonResponse> getPersons() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::toDto)
                .toList();
    }

    @Override
    public PersonResponse getPersonByUsername(String username) {
        return null;
    }
}
