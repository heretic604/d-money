package com.heretic.dmoney.mappers;

import com.heretic.dmoney.dto.requests.PersonRequest;
import com.heretic.dmoney.dto.requests.WalletRequest;
import com.heretic.dmoney.dto.responses.WalletResponse;
import com.heretic.dmoney.entities.Person;
import com.heretic.dmoney.entities.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface WalletMapper {

    WalletMapper INSTANCE = Mappers.getMapper(WalletMapper.class);

    Wallet toEntity(WalletRequest dto);

    WalletResponse toDto(Wallet entity);

    Wallet update(WalletRequest walletRequest, @MappingTarget Wallet wallet);

}