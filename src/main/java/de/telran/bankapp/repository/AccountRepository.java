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

    public Optional<Account> findById(long id) {
        return accounts.stream().filter(account -> account.getId().equals(id)).findAny();
    }

    public List<Account> getAllAccountsByCurrencyCode(String currencyCode) {
        CurrencyCode currencyCode1 = CurrencyCode.valueOf(currencyCode.toUpperCase());
        return accounts.stream().filter(account -> account.getCurrencyCode() == currencyCode1).toList();
    }

    public List<Account> getAllAccountsByCurrencyCodeBalance(BigDecimal minValue, BigDecimal maxValue) {
        return accounts.stream()
                .filter(account -> account.getBalance().compareTo(minValue) >= 0 && account.getBalance().compareTo(maxValue) <= 0)
                .toList();
    }
    public Account update(Account updatedAccount) {

        Account account = findById(updatedAccount.getId()).orElse(new Account());
       if (updatedAccount.getName() != null && !updatedAccount.getName().isEmpty()){
           account.setName(updatedAccount.getName());
       };
       if(updatedAccount.getBalance() != null) {
           account.setBalance(updatedAccount.getBalance());
       }
       if(updatedAccount.getCurrencyCode() != null) {
           account.setCurrencyCode(updatedAccount.getCurrencyCode());
       }
       if( updatedAccount.getStatus() != null) {
           account.setStatus(updatedAccount.getStatus());
       }
       if (updatedAccount.getType() != null) {
           account.setType(updatedAccount.getType());
       }

        return account;
    }

    public Account create(Account account) {
        accounts.add(account);
        return account;
    }

    public void delete(Long id) {
        Account account = findById(id).orElse(new Account());

        if(account.getName() != null) {
        accounts.remove(account);
        }
    }
}
