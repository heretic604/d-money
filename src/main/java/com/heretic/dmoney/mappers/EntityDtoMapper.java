package com.heretic.dmoney.mappers;

import com.heretic.dmoney.dto.requests.OperationRequest;
import com.heretic.dmoney.dto.requests.PersonRequest;
import com.heretic.dmoney.dto.requests.WalletRequest;
import com.heretic.dmoney.dto.responses.OperationResponse;
import com.heretic.dmoney.dto.responses.PersonResponse;
import com.heretic.dmoney.dto.responses.WalletResponse;
import com.heretic.dmoney.entities.Operation;
import com.heretic.dmoney.entities.Person;
import com.heretic.dmoney.entities.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface EntityDtoMapper {

    Person personDTOtoEntity(PersonRequest dto);

    @Mapping(source = "wallets", target = "walletResponses")
    PersonResponse personEntityToDTO(Person entity);

    Person updatePersonEntity(PersonRequest personRequest, @MappingTarget Person person);

    Wallet walletDTOtoEntity(WalletRequest dto);

    @Mapping(source = "person.personId", target = "personID")
    WalletResponse walletEntityToDTO(Wallet entity);

    Wallet updateWalletEntity(WalletRequest walletRequest, @MappingTarget Wallet wallet);

    Operation operationDTOtoEntity(OperationRequest dto);

    OperationResponse operationEntityToDTO(Operation entity);
}