package com.heretic.dmoney.services;

import com.heretic.dmoney.dto.requests.PersonRequest;
import com.heretic.dmoney.dto.responses.PersonResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PersonService {

    PersonResponse savePerson(PersonRequest personRequest);

    PersonResponse getPerson(UUID id);

    PersonResponse getPerson(String username);

    List<PersonResponse> getPersons();

//    PersonResponse updatePerson(PersonRequest personRequest, UUID id);

    boolean deletePerson(UUID id);
}