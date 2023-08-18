package com.heretic.dmoney.services;

import com.heretic.dmoney.dto.responses.PersonResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PersonService {

    PersonResponse getPerson(UUID id);

    List<PersonResponse> getPersons();

    PersonResponse getPersonByUsername(String username);
}