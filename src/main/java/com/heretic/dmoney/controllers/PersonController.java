package com.heretic.dmoney.controllers;

import com.heretic.dmoney.dto.responses.PersonResponse;
import com.heretic.dmoney.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping(value = "person/{id}")
    public PersonResponse getPerson(@PathVariable UUID id) {
        return personService.getPerson(id);
    }

    @GetMapping(value = "personByLogin/{username}")
    public PersonResponse getPersonByUsername(@PathVariable String username) {
        return personService.getPersonByUsername(username);
    }

    @GetMapping("/persons")
    public List<PersonResponse> getPersons() {
        return personService.getPersons();
    }
}