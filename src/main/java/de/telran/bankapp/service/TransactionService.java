package de.telran.bankapp.service;

import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.Transaction;
import de.telran.bankapp.entity.enums.TransactionStatus;
import de.telran.bankapp.entity.enums.TransactionType;
import de.telran.bankapp.exception.BankAppBadRequestException;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
import de.telran.bankapp.repository.AccountRepository;
import de.telran.bankapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static de.telran.bankapp.entity.enums.TransactionType.TRANSFER;

@Service
@Transactional(readOnly = true)
public class TransactionService {


    private final TransactionRepository repository;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionService(TransactionRepository repository, AccountRepository accountRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public Transaction getTransactionById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new BankAppResourceNotFoundException("Transaction with id = " + id + " not found in database"));
    }

    public List<Transaction> findTransactionsByTypeAndAmount(TransactionType type, BigDecimal minAmount) {
        return repository.findAllByTypeAndAmountIsGreaterThanEqual(type, minAmount);
    }

    public List<Transaction> findTransactionsByType(TransactionType type) {
        return repository.findAllByType(type);
    }

    public List<Transaction> findTransactionByStatusNotAndAmountBetween(TransactionStatus status, BigDecimal minAmount, BigDecimal maxAmount) {
        BigDecimal min = minAmount == null ? new BigDecimal("0.00") : minAmount;
        BigDecimal max = maxAmount == null ? new BigDecimal("50000.00") : maxAmount;
        return repository.findTransactionByStatusNotAndAmountBetween(status, min, max);
    }

    public List<Transaction> findTransactionsByTypeAndByStatus(TransactionType type, TransactionStatus status) {
        return repository.nativeQuery(type, status);
    }

    @Transactional
    public Transaction addTransaction(Transaction transaction) {
        return repository.save(transaction);
    }

    @Transactional
    public Transaction updateTransaction(Transaction transaction) {
        String id = transaction.getId();
        Optional<Transaction> optional = repository.findById(id);
        if (optional.isPresent()) {
            return repository.save(transaction);
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
    public void transferMoney(Long fromId, Long toId, BigDecimal amount) {
        Optional<Account> fromAccountOptional = accountRepository.findById(fromId);
        Optional<Account> toAccountOptional = accountRepository.findById(toId);

        if (fromAccountOptional.isEmpty() || toAccountOptional.isEmpty()) {
            throw new BankAppResourceNotFoundException("Client with id = " + fromId + " or with id = " + toId + " not found in database");
        }
        Account fromAccount = fromAccountOptional.get();
        Account toAccount = toAccountOptional.get();

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new BankAppBadRequestException("Insufficient funds for transfer.");
        }

        if (!fromAccount.getCurrencyCode().equals(toAccount.getCurrencyCode())) {
            throw new BankAppBadRequestException("Currency code is not correct.");
        }
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);


        Transaction transaction = new Transaction(null, TRANSFER, amount,
                "Money transferred from account " + fromId + " to " + toId,
                TransactionStatus.COMPLETED, toId, fromId);

        repository.save(transaction);
    }


}

