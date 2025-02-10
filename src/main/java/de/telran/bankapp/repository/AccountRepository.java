package de.telran.bankapp.repository;

import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.enums.CurrencyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.balance BETWEEN :minValue AND :maxValue")
    List<Account> getAllAccountsByCurrencyCodeBalance(@Param("minValue") BigDecimal minValue, @Param("maxValue") BigDecimal maxValue);

    List<Account> getAllAccountsByCurrencyCode(CurrencyCode currencyCode);


}
