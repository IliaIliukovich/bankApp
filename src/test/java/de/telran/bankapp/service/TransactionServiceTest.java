package de.telran.bankapp.service;

import de.telran.bankapp.dto.TransactionDto;
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
    void findTransactionByStatusNotAndAmountBetween() {
        service.findTransactionByStatusNotAndAmountBetween(TransactionStatus.NEW, null, null);
        verify(repository)
                .findTransactionByStatusNotAndAmountBetween(TransactionStatus.NEW, new BigDecimal("0.00"), new BigDecimal("50000.00"));

    }
}