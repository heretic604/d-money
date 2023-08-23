package com.heretic.dmoney.mappers;

import com.heretic.dmoney.dto.requests.OperationRequest;
import com.heretic.dmoney.dto.responses.OperationResponse;
import com.heretic.dmoney.entities.Operation;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring")
@Component
public interface OperationMapper {

    OperationMapper INSTANCE = getMapper(OperationMapper.class);

    Operation toEntity(OperationRequest dto);

    OperationResponse toDto(Operation entity);
}