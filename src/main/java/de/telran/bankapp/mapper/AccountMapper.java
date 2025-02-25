package de.telran.bankapp.mapper;

import de.telran.bankapp.dto.AccountCreateDto;
import de.telran.bankapp.dto.AccountDto;
import de.telran.bankapp.dto.AccountPostCreateDto;
import de.telran.bankapp.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDto entityToDto(Account entity);

    List<AccountDto> entityListToDto(List<Account> accounts);

    Account createPostDtoToEntity(AccountPostCreateDto dto);

    Account dtoToEntity(AccountDto dto);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "currencyCode", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "type", source = "accountType")
    @Mapping(target = "balance", source = "initialAmount")
    Account createDtoToEntity(AccountCreateDto dto);

}
