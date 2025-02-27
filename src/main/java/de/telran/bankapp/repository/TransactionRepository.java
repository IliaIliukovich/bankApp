package de.telran.bankapp.repository;

import de.telran.bankapp.entity.Transaction;
import de.telran.bankapp.entity.enums.TransactionStatus;
import de.telran.bankapp.entity.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findAllByTypeAndAmountIsGreaterThanEqual(TransactionType type, BigDecimal amount);

    List<Transaction> findAllByType(TransactionType type);

    List<Transaction> findTransactionByStatusNotAndAmountBetween(TransactionStatus status, BigDecimal minAmount, BigDecimal maxAmount);

    @NativeQuery("select * from transaction t where t.type = :#{#type?.name()} and t.status = :#{#status?.name()}")
    List<Transaction> findByTypeAndStatus(TransactionType type, TransactionStatus status);

    @Query("update Transaction t set t.status = :status where t.id = :id")
    @Modifying
    int updateStatus(String id, TransactionStatus status);

    @Modifying
    void deleteAllByStatus(TransactionStatus status);

}
