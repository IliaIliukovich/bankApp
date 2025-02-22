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

    Transaction dtoToEntity(TransactionDto dto);

    TransactionDto entityToDto(Transaction transaction);

    List<TransactionDto> entityListToDto(List<Transaction> transactions);

    @Mapping(target = "status", constant = "NEW")
    @Mapping(target = "type", defaultValue = "PAYMENT")
    @Mapping(target = "id", ignore = true)
    Transaction createDtoToEntity(TransactionCreateDto dto);

    @Mapping(target = "status", constant = "COMPLETED")
    @Mapping(target = "debitAccountId", source = "toId")
    @Mapping(target = "creditAccountId", source = "fromId")
    @Mapping(target = "type", source = "transactionType")
    @Mapping(target = "amount", source = "moneyAmount")
    @Mapping(target = "id", ignore = true)
    Transaction createTransactionTransferDtoToEntity(TransactionTransferDto dto);

}
