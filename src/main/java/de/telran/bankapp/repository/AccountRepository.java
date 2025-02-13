package de.telran.bankapp.repository;

import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.enums.CurrencyCode;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.balance BETWEEN :minValue AND :maxValue")
    List<Account> getAllAccountsByBalance(@Param("minValue") BigDecimal minValue, @Param("maxValue") BigDecimal maxValue);

    @Query("SELECT a FROM Account a WHERE a.currencyCode= :currencyCode")
    List<Account> getAllAccountsByCurrencyCode(CurrencyCode currencyCode);

   

    @Query ("DELETE FROM Account a WHERE a.id = :id")
    @Modifying
    @Transactional
    void deleteAccountById(Long id);

    }



