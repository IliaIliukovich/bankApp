package de.telran.bankapp.mapper;

import de.telran.bankapp.dto.TransactionCreateDto;
import de.telran.bankapp.dto.TransactionDto;
import de.telran.bankapp.dto.TransactionTransferDto;
import de.telran.bankapp.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "debitAccount", ignore = true)
    @Mapping(target = "creditAccount", ignore = true)
    Transaction dtoToEntity(TransactionDto dto);

    @Mapping(target = "debitAccountId", source = "debitAccount.id")
    @Mapping(target = "creditAccountId", source = "creditAccount.id")
    TransactionDto entityToDto(Transaction transaction);

    List<TransactionDto> entityListToDto(List<Transaction> transactions);

    @Mapping(target = "debitAccount", ignore = true)
    @Mapping(target = "creditAccount", ignore = true)
    @Mapping(target = "status", constant = "NEW")
    @Mapping(target = "type", defaultValue = "PAYMENT")
    @Mapping(target = "id", ignore = true)
    Transaction createDtoToEntity(TransactionCreateDto dto);

    @Mapping(target = "status", constant = "COMPLETED")
    @Mapping(target = "debitAccount.id", source = "toId")
    @Mapping(target = "creditAccount.id", source = "fromId")
    @Mapping(target = "type", source = "transactionType")
    @Mapping(target = "amount", source = "moneyAmount")
    @Mapping(target = "id", ignore = true)
    Transaction createTransactionTransferDtoToEntity(TransactionTransferDto dto);

}
