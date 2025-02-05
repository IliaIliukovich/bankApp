package de.telran.bankapp.repository;

import de.telran.bankapp.entity.Transaction;
import de.telran.bankapp.entity.enums.TransactionStatus;
import de.telran.bankapp.entity.enums.TransactionType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TransactionRepository {

    List<Transaction> transactions = new ArrayList<>();

    public TransactionRepository() {
        transactions.add(new Transaction(UUID.randomUUID().toString(), TransactionType.PAYMENT, new BigDecimal("12.0"), "description", TransactionStatus.APPROVED, 1L, 2L));
        transactions.add(new Transaction(UUID.randomUUID().toString(), TransactionType.PAYMENT, new BigDecimal("23.0"), "description", TransactionStatus.PENDING, 1L, 3L));
        transactions.add(new Transaction(UUID.randomUUID().toString(), TransactionType.PAYMENT, new BigDecimal("200.0"), "description", TransactionStatus.NEW, 2L, 3L));
    }


    public List<Transaction> getAll() {
        return transactions;
    }

    public Optional<Transaction> findById(String id) {
        return transactions.stream().filter(t -> t.getId().equals(id)).findAny();
    }

    public Transaction addTransaction(Transaction transaction) {
        transaction.setId(UUID.randomUUID().toString());
        transactions.add(transaction);
        return transaction;
    }

}
