package de.telran.bankapp.service;

import de.telran.bankapp.entity.Transaction;
import de.telran.bankapp.entity.enums.TransactionStatus;
import de.telran.bankapp.entity.enums.TransactionType;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
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

    private TransactionRepository repository;

    @Autowired
    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
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
}

