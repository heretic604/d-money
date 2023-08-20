package com.heretic.dmoney.mappers;

import com.heretic.dmoney.dto.requests.OperationResponse;
import com.heretic.dmoney.dto.responses.OperationRequest;
import com.heretic.dmoney.entities.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface OperationMapper {

    OperationMapper INSTANCE = Mappers.getMapper(OperationMapper.class);

    Operation toEntity(OperationRequest dto);

    OperationResponse toDto(Operation entity);
}