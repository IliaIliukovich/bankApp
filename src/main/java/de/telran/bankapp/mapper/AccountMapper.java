package de.telran.bankapp.mapper;

import de.telran.bankapp.dto.AccountCreateDto;
import de.telran.bankapp.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "currencyCode", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "type", source = "accountType")
    @Mapping(target = "balance", source = "initialAmount")
    Account createDtoToEntity(AccountCreateDto dto);

}
