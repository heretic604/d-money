package com.heretic.dmoney.controllers;

import com.heretic.dmoney.aspects.ExcludeLogging;
import com.heretic.dmoney.dto.requests.PersonRequest;
import com.heretic.dmoney.dto.responses.PersonResponse;
import com.heretic.dmoney.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Person Controller", description = "Description")
public class PersonController {

    private final PersonService personService;

    @PostMapping(value = "/person")
    public PersonResponse savePerson(@Valid @RequestBody PersonRequest personRequest) {
        return personService.savePerson(personRequest);
    }

    @Operation(description = "method desc")
    @ApiResponse(responseCode = "200")
    @GetMapping(value = "/person/{id}")
    public PersonResponse getPerson(@PathVariable UUID id) {
        return personService.getPerson(id);
    }

    @GetMapping(value = "/personByLogin/{username}")
    public PersonResponse getPersonByUsername(@PathVariable String username) {
        return personService.getPerson(username);
    }

    @ExcludeLogging
    @GetMapping(value = "/persons")
    public List<PersonResponse> getPersons() {
        return personService.getPersons();
    }

    @PutMapping(value = "/person/{id}")
    public PersonResponse updatePerson(@RequestBody PersonRequest personRequest, @PathVariable UUID id) {
        return personService.updatePerson(personRequest, id);
    }

    @DeleteMapping(value = "/person/{id}")
    public boolean deletePerson(@PathVariable UUID id) {
        return personService.deletePerson(id);
    }
}