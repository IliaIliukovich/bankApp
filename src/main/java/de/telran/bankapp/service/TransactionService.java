package de.telran.bankapp.service;

import de.telran.bankapp.dto.TransactionCreateDto;
import de.telran.bankapp.dto.TransactionDto;
import de.telran.bankapp.dto.TransactionTransferDto;
import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.Transaction;
import de.telran.bankapp.entity.enums.TransactionStatus;
import de.telran.bankapp.entity.enums.TransactionType;
import de.telran.bankapp.exception.BankAppBadRequestException;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
import de.telran.bankapp.mapper.TransactionMapper;
import de.telran.bankapp.repository.AccountRepository;
import de.telran.bankapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TransactionService {


    private final TransactionRepository repository;
    private final AccountRepository accountRepository;
    private final TransactionMapper mapper;
    private final CurrencyService currencyService;

    @Autowired
    public TransactionService(TransactionRepository repository, AccountRepository accountRepository, TransactionMapper mapper, CurrencyService currencyService) {
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.mapper = mapper;
        this.currencyService = currencyService;
    }

    public List<TransactionDto> getAllTransactions() {
        List<Transaction> transactions = repository.findAll();
        return mapper.entityListToDto(transactions);
    }

    public Optional<TransactionDto> getTransactionById(String id) {
        Optional<Transaction> optional = repository.findById(id);
        TransactionDto transactionDto = mapper.entityToDto(optional.orElse(null));
        return Optional.ofNullable(transactionDto);
    }

    public List<TransactionDto> findTransactionsByTypeAndAmount(TransactionType type, BigDecimal minAmount) {
        List<Transaction> transactions = repository.findAllByTypeAndAmountIsGreaterThanEqual(type, minAmount);
        return mapper.entityListToDto(transactions);
    }

    public List<TransactionDto> findTransactionsByType(TransactionType type) {
        List<Transaction> transactions = repository.findAllByType(type);
        return mapper.entityListToDto(transactions);
    }

    public List<TransactionDto> findTransactionByStatusNotAndAmountBetween(TransactionStatus status, BigDecimal minAmount, BigDecimal maxAmount) {
        BigDecimal min = minAmount == null ? new BigDecimal("0.00") : minAmount;
        BigDecimal max = maxAmount == null ? new BigDecimal("50000.00") : maxAmount;
        List<Transaction> transactions = repository.findTransactionByStatusNotAndAmountBetween(status, min, max);
        return mapper.entityListToDto(transactions);
    }

    public List<TransactionDto> findByTypeAndStatus(TransactionType type, TransactionStatus status) {
        List<Transaction> transactions = repository.findByTypeAndStatus(type, status);
        return mapper.entityListToDto(transactions);
    }

    @Transactional
    public TransactionDto addTransaction(TransactionCreateDto dto) {
        Transaction transaction = mapper.createDtoToEntity(dto);
        Account creditAccount = accountRepository.getReferenceById(dto.getCreditAccountId());
        Account debitAccount = accountRepository.getReferenceById(dto.getDebitAccountId());
        transaction.setCreditAccount(creditAccount);
        transaction.setDebitAccount(debitAccount);
        Transaction saved = repository.save(transaction);
        return mapper.entityToDto(saved);
    }

    @Transactional
    public TransactionDto updateTransaction(TransactionDto dto) {
        String id = dto.getId();
        Optional<Transaction> optional = repository.findById(id);
        if (optional.isPresent()) {
            Transaction transaction = mapper.dtoToEntity(dto);
            Account creditAccount = accountRepository.getReferenceById(dto.getCreditAccountId());
            Account debitAccount = accountRepository.getReferenceById(dto.getDebitAccountId());
            transaction.setDebitAccount(debitAccount);
            transaction.setCreditAccount(creditAccount);
            Transaction saved = repository.save(transaction);
            return mapper.entityToDto(saved);
        }
        throw new BankAppResourceNotFoundException("Transaction with id = " + id + " not found in database");
    }

    @Transactional
    public void changeStatusById(String id, TransactionStatus status) {
        int integer = repository.updateStatus(id, status);
        if (integer == 0)
            throw new BankAppResourceNotFoundException("Transaction with id = " + id + " not found in database");
    }

    @Transactional
    public void deleteNewTransactions() {
        repository.deleteAllByStatus(TransactionStatus.NEW);
    }

    @Transactional
    public void transferMoney(TransactionTransferDto dto) {
        Long fromId = dto.getFromId();
        Long toId = dto.getToId();
        BigDecimal amount = new BigDecimal(dto.getMoneyAmount());

        Optional<Account> fromAccountOptional = accountRepository.findById(fromId);
        Optional<Account> toAccountOptional = accountRepository.findById(toId);

        validateTransactionDto(dto, fromAccountOptional, toAccountOptional);

        Account fromAccount = fromAccountOptional.get();
        Account toAccount = toAccountOptional.get();

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        BigDecimal convertedAmount = currencyService.convertAmountToRequiredCurrency(
                amount,
                fromAccount.getCurrencyCode(),
                toAccount.getCurrencyCode(),
                currencyService.getRates()
                );
        toAccount.setBalance(toAccount.getBalance().add(convertedAmount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = mapper.createTransactionTransferDtoToEntity(dto);
        repository.save(transaction);
    }

    private void validateTransactionDto(TransactionTransferDto dto,
                                        Optional<Account> fromAccountOptional,
                                        Optional<Account> toAccountOptional) {

        if (fromAccountOptional.isEmpty() || toAccountOptional.isEmpty()) {
            throw new BankAppResourceNotFoundException("Client with id = " + dto.getFromId() + " or with id = " + dto.getToId() + " not found in database");
        }

        Account fromAccount = fromAccountOptional.get();
        if (fromAccount.getBalance().compareTo(new BigDecimal(dto.getMoneyAmount())) < 0) {
            throw new BankAppBadRequestException("Insufficient funds for transfer");
        }
    }


}

