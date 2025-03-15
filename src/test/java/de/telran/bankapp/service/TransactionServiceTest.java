package de.telran.bankapp.service;

import de.telran.bankapp.dto.TransactionCreateDto;
import de.telran.bankapp.dto.TransactionDto;
import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.Transaction;
import de.telran.bankapp.entity.enums.TransactionStatus;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
import de.telran.bankapp.mapper.TransactionMapper;
import de.telran.bankapp.repository.AccountRepository;
import de.telran.bankapp.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionMapper mapper;

    @InjectMocks
    private TransactionService service;

    @Test
    void getTransactionByIdNotFound() {
        String uuid = "123";
        when(repository.findById(uuid)).thenReturn(Optional.empty());
        Optional<TransactionDto> optional = service.getTransactionById(uuid);
        assertTrue(optional.isEmpty());
    }

    @Test
    void getTransactionByIdFound() {
        String uuid = "123";
        Transaction transaction = new Transaction();
        transaction.setId(uuid);
        when(repository.findById(uuid)).thenReturn(Optional.of(transaction));

        TransactionDto dto = new TransactionDto();
        dto.setId(uuid);
        when(mapper.entityToDto(transaction)).thenReturn(dto);
        Optional<TransactionDto> optional = service.getTransactionById(uuid);
        assertEquals(uuid, optional.get().getId());
    }

    @Test
    void findTransactionByStatusNotWithoutAmount() {
        service.findTransactionByStatusNotAndAmountBetween(TransactionStatus.NEW, null, null);
        verify(repository)
                .findTransactionByStatusNotAndAmountBetween(TransactionStatus.NEW, new BigDecimal("0.00"), new BigDecimal("50000.00"));

    }

    @Test
    void findTransactionByStatusNotWithMinAmount() {
        service.findTransactionByStatusNotAndAmountBetween(TransactionStatus.NEW, new BigDecimal("100.00"), null);
        verify(repository)
                .findTransactionByStatusNotAndAmountBetween(TransactionStatus.NEW, new BigDecimal("100.00"), new BigDecimal("50000.00"));

    }

    @Test
    void findTransactionByStatusNotWithMaxAmount() {
        service.findTransactionByStatusNotAndAmountBetween(TransactionStatus.NEW, null, new BigDecimal("500.00"));
        verify(repository)
                .findTransactionByStatusNotAndAmountBetween(TransactionStatus.NEW, new BigDecimal("0.00"), new BigDecimal("500.00"));

    }

    @Test
    void findTransactionByStatusNotWithAmount() {
        service.findTransactionByStatusNotAndAmountBetween(TransactionStatus.NEW, new BigDecimal("100.00"), new BigDecimal("500.00"));
        verify(repository)
                .findTransactionByStatusNotAndAmountBetween(TransactionStatus.NEW, new BigDecimal("100.00"), new BigDecimal("500.00"));

    }

    @Test
    void addTransaction() {
        TransactionCreateDto dto = new TransactionCreateDto
                ("PAYMENT", "10.00", "descr", 1L, 2L);
        Transaction transaction = new Transaction();
        transaction.setId("123");
        when(mapper.createDtoToEntity(dto)).thenReturn(transaction);

        Account creditAccount = new Account();
        creditAccount.setId(2L);
        when(accountRepository.getReferenceById(dto.getCreditAccountId())).thenReturn(creditAccount);

        Account debitAccount = new Account();
        debitAccount.setId(1L);
        when(accountRepository.getReferenceById(dto.getDebitAccountId())).thenReturn(debitAccount);

        service.addTransaction(dto);

        assertEquals(creditAccount, transaction.getCreditAccount());
        assertEquals(debitAccount, transaction.getDebitAccount());

        verify(repository).save(transaction);
        verify(mapper).entityToDto(any());
    }
}