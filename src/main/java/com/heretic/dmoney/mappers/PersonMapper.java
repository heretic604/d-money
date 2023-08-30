package com.heretic.dmoney.mappers;

import com.heretic.dmoney.dto.requests.PersonRequest;
import com.heretic.dmoney.dto.responses.PersonResponse;
import com.heretic.dmoney.entities.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface PersonMapper {

    @Mapping(target = "registrationTime", defaultExpression = "java(java.time.LocalDateTime.now())")
    Person mapToPerson(PersonRequest dto);

    @Mapping(source = "wallets", target = "walletResponses")
    PersonResponse mapToPersonResponse(Person entity);

    Person updatePerson(PersonRequest personRequest, @MappingTarget Person person);
}