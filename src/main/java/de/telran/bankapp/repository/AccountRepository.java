package de.telran.bankapp.repository;

import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.enums.AccountStatus;
import de.telran.bankapp.entity.enums.AccountType;
import de.telran.bankapp.entity.enums.CurrencyCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountRepository {

    public ResponseEntity<Account> account;

    List<Account> accounts = new ArrayList<>();

    public AccountRepository() {

        accounts.add(new Account(1L, "John Doe", AccountType.CHECKING, AccountStatus.ACTIVE, new BigDecimal("1500.75"), CurrencyCode.USD));
        accounts.add(new Account(2L, "Alice Smith", AccountType.SAVINGS, AccountStatus.ACTIVE, new BigDecimal("10200.00"), CurrencyCode.EUR));
        accounts.add(new Account(3L, "Bob Johnson", AccountType.LOAN, AccountStatus.BLOCKED, new BigDecimal("-5000.50"), CurrencyCode.USD));
        accounts.add(new Account(4L, "Emma Brown", AccountType.DEBIT_CARD, AccountStatus.INACTIVE, new BigDecimal("200.00"), CurrencyCode.GBR));
        accounts.add(new Account(5L, "David Wilson", AccountType.CREDIT_CARD, AccountStatus.ACTIVE, new BigDecimal("-1200.00"), CurrencyCode.EUR));
        accounts.add(new Account(6L, "Sophia Martinez", AccountType.CHECKING, AccountStatus.BLOCKED, new BigDecimal("500.25"), CurrencyCode.USD));
        accounts.add(new Account(7L, "James Anderson", AccountType.SAVINGS, AccountStatus.INACTIVE, new BigDecimal("2500.00"), CurrencyCode.GBR));
        accounts.add(new Account(8L, "Olivia Taylor", AccountType.OTHER, AccountStatus.ACTIVE, new BigDecimal("75.90"), CurrencyCode.EUR));
        accounts.add(new Account(9L, "Michael White", AccountType.DEBIT_CARD, AccountStatus.BLOCKED, new BigDecimal("0.00"), CurrencyCode.USD));
        accounts.add(new Account(10L, "Emily Harris", AccountType.CREDIT_CARD, AccountStatus.ACTIVE, new BigDecimal("-300.40"), CurrencyCode.GBR));

    }

    public List<Account> findAll() {
        return accounts;
    }

    public Optional<Object> findById(long id) {
        return Optional.empty();
    }

    public Account save(Account account) {
        accounts.add(account);
        return account;
    }

    public void delete(Long id) {
        accounts.remove(id);
    }
}
