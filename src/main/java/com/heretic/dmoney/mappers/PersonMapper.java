package com.heretic.dmoney.mappers;

import com.heretic.dmoney.dto.requests.PersonRequest;
import com.heretic.dmoney.dto.responses.PersonResponse;
import com.heretic.dmoney.entities.Person;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Mapper
@Component
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    Person toEntity(PersonRequest dto);

    PersonResponse toDto(Person entity);

    Person update(PersonRequest personRequest, @MappingTarget Person person);
}