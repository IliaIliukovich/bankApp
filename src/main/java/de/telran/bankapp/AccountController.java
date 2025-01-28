package de.telran.bankapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final List<Account> accounts = new ArrayList<>();

    {
        accounts.add(new Account(1L, "John Doe", AccountType.SAVINGS, AccountStatus.ACTIVE, new BigDecimal("1500.50"), CurrencyCode.USD));
        accounts.add(new Account(2L, "Jane Smith", AccountType.CHECKING, AccountStatus.INACTIVE, new BigDecimal("2500.75"), CurrencyCode.EUR));
        accounts.add(new Account(3L, "Mike Johnson", AccountType.DEBIT_CARD, AccountStatus.BLOCKED, new BigDecimal("5000.00"), CurrencyCode.GBR));
    }

    //    - REST запрос на вывод списка всех счетов
    @GetMapping("/")
    public List<Account> getAccounts() {
        return accounts;
    }

    //      - REST запрос на вывод одного счета по id
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
        return accounts.stream()
                .filter(account -> account.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //      - REST запрос на вывод всех счетов определенной валюты
    @GetMapping("/currency/{code}")
    public List<Account> getCurrency(@PathVariable String code) {
        return accounts.stream()
                .filter(account -> account.getCurrencyCode().name().equalsIgnoreCase(code))
                .toList();
    }

    //      - REST запрос на вывод всех счетов, баланс которых находится в пределах от minValue до maxValue
    @GetMapping("/balance")
    public List<Account> getRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return accounts.stream()
                .filter(account ->
                        account.getBalance().compareTo(min) >= 0 &&
                                account.getBalance().compareTo(max) <= 0
                )
                .toList();
    }

}
