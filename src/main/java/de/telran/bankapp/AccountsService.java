package de.telran.bankapp;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountsService {

    private final List<Account> accounts = List.of(

            Account.builder()
                    .id(1L)
                    .name("Hans Schmidt")
                    .type(AccountType.CHECKING)
                    .status(AccountStatus.ACTIVE)
                    .balance(new BigDecimal("1500"))
                    .currencyCode(CurrencyCode.USD)
                    .build(),

            new Account(2L, "Anna Müller", AccountType.SAVINGS, AccountStatus.ACTIVE, new BigDecimal("3200.50"), CurrencyCode.EUR),
            new Account(3L, "Peter Klein", AccountType.LOAN, AccountStatus.BLOCKED, new BigDecimal("5000.75"), CurrencyCode.USD),
            new Account(4L, "Maria Schneider", AccountType.CREDIT_CARD, AccountStatus.ACTIVE, new BigDecimal("200.00"), CurrencyCode.GBR),
            new Account(5L, "Lukas Fischer", AccountType.DEBIT_CARD, AccountStatus.INACTIVE, new BigDecimal("980.00"), CurrencyCode.USD),
            new Account(6L, "Sophie Weber", AccountType.CHECKING, AccountStatus.ACTIVE, new BigDecimal("1500.00"), CurrencyCode.USD),
            new Account(7L, "Max Meyer", AccountType.SAVINGS, AccountStatus.ACTIVE, new BigDecimal("3200.50"), CurrencyCode.EUR),
            new Account(8L, "Julia Wagner", AccountType.LOAN, AccountStatus.BLOCKED, new BigDecimal("5000.75"), CurrencyCode.USD),
            new Account(9L, "Anna Becker", AccountType.CREDIT_CARD, AccountStatus.ACTIVE, new BigDecimal("200.00"), CurrencyCode.GBR),
            new Account(10L, "Clara Hoffmann", AccountType.DEBIT_CARD, AccountStatus.INACTIVE, new BigDecimal("980.00"), CurrencyCode.USD)
    );


    public List<Account> getAllAccounts() {
        return accounts;
    }


    public Optional<Account> getAccountById(Long id) {
        return accounts.stream()
                .filter(account -> account.getId().equals(id))
                .findFirst();
    }


    public List<Account> getAccountsByCurrency(CurrencyCode currency) {
        return accounts.stream()
                .filter(account -> account.getCurrencyCode() == currency)
                .collect(Collectors.toList());
    }


    public List<Account> getAccountsByBalanceRange(BigDecimal min, BigDecimal max) {
        return accounts.stream()
                .filter(account -> account.getBalance().compareTo(min) >= 0 && account.getBalance().compareTo(max) <= 0)
                .collect(Collectors.toList());
    }

}
