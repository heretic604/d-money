package com.heretic.dmoney.mappers;

import com.heretic.dmoney.dto.requests.WalletRequest;
import com.heretic.dmoney.dto.responses.WalletResponse;
import com.heretic.dmoney.entities.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface WalletMapper {

    @Mapping(target = "amount", defaultExpression = "java(java.math.BigDecimal.valueOf(0))")
    Wallet mapToWallet(WalletRequest dto);

    Wallet mapToWallet(WalletResponse dto);

    @Mapping(source = "person.personId", target = "personId")
    WalletResponse mapToWalletResponse(Wallet entity);
}