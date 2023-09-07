package com.heretic.dmoney.mappers;

import com.heretic.dmoney.dto.requests.OperationRequest;
import com.heretic.dmoney.dto.responses.OperationResponse;
import com.heretic.dmoney.entities.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface OperationMapper {

    @Mapping(target = "time", defaultExpression = "java(java.time.LocalDateTime.now())")
    Operation mapToOperation(OperationRequest dto);

    @Mapping(source = "sender.walletNumber", target = "senderNumber")
    @Mapping(source = "receiver.walletNumber", target = "receiverNumber")
    @Mapping(source = "exRate", target = "exRate")
    @Mapping(target = "currencyOut", defaultExpression = "java(entity.getSender().getCurrency())")
    @Mapping(target = "currencyIn", defaultExpression = "java(entity.getReceiver().getCurrency())")
    OperationResponse mapToOperationResponse(Operation entity);
}