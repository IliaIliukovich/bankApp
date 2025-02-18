package de.telran.bankapp.service;

import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.Transaction;
import de.telran.bankapp.entity.enums.TransactionStatus;
import de.telran.bankapp.entity.enums.TransactionType;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
import de.telran.bankapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static de.telran.bankapp.entity.enums.TransactionType.TRANSFER;

@Service
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

    public Transaction addTransaction(Transaction transaction) {
        return repository.save(transaction);
    }

    public Transaction updateTransaction(Transaction transaction) {
        String id = transaction.getId();
        Optional<Transaction> optional = repository.findById(id);
        if (optional.isPresent()) {
            return repository.save(transaction);
        }
        throw new BankAppResourceNotFoundException("Transaction with id = " + id + " not found in database");
    }

    public void changeStatusById(String id, TransactionStatus status) {
        int integer = repository.updateStatus(id, status);
        if (integer == 0)
            throw new BankAppResourceNotFoundException("Transaction with id = " + id + " not found in database");
    }

    public void deleteNewTransactions() {
        repository.deleteAllByStatus(TransactionStatus.NEW);
    }


    public void transferMoney(Long fromId, Long toId, BigDecimal amount) {
        Account fromAccount = accountRepository.findById(fromId)
                .orElseThrow(() -> new NoSuchElementException("Sender account not found"));

        Account toAccount = accountRepository.findById(toId)
                .orElseThrow(() -> new NoSuchElementException("Receiver account not found"));


                    if (fromAccount.getBalance().compareTo(amount) < 0) {
                        throw new InsufficientFundsException("Insufficient funds for transfer.");
        }


        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);


                Transaction transaction = new Transaction(null, TRANSFER, amount,
                "Money transferred from account " + fromId + " to " + toId,
                TransactionStatus.COMPLETED, fromId, toId);

        repository.save(transaction);
    }



}

