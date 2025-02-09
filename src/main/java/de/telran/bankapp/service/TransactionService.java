package de.telran.bankapp.service;

import de.telran.bankapp.entity.Transaction;
import de.telran.bankapp.entity.enums.TransactionStatus;
import de.telran.bankapp.entity.enums.TransactionType;
import de.telran.bankapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private TransactionRepository repository;

    @Autowired
    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public Optional<Transaction> getTransactionById(String id) {
        return repository.findById(id);
    }

    public Transaction addTransaction(Transaction transaction) {
        return repository.save(transaction);
    }

    public Optional<Transaction> changeStatusById(String id, TransactionStatus status) {
        Optional<Transaction> optional = repository.findById(id);
        if (optional.isPresent()) {
            Transaction transaction = optional.get();
            transaction.setStatus(status);
            return Optional.of(repository.save(transaction));
        } else {
            return Optional.empty();
        }
    }

    public List<Transaction> findTransactionsByTypeAndAmount(TransactionType type, BigDecimal minAmount) {
        return repository.findAll().stream()
                .filter(t -> t.getType().equals(type) && t.getAmount().compareTo(minAmount) >= 0)
                .toList();
    }

    public void deleteNewTransactions() {
        repository.deleteAll(repository.findAll().stream()
                .filter(t -> t.getStatus().equals(TransactionStatus.NEW))
                .toList());
    }

    public Optional<Transaction> updateTransaction(Transaction transaction) {
        String id = transaction.getId();
        Optional<Transaction> optional = repository.findById(id);
        if (optional.isPresent()) {
            return Optional.of(repository.save(transaction));
        }
        return Optional.empty();
    }
}

