package de.telran.bankapp.controller;

import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.enums.CurrencyCode;
import de.telran.bankapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {

    private AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }


    @GetMapping("/all")
    public List<Account> getAll() {

        return service.getAll();
    }

    @GetMapping("{id}")
    public Optional<Account> getAccountById(@PathVariable Long id) {
        return accounts.stream().filter(account -> account.getId().equals(id)).findAny();
    }


    @GetMapping("/currencyCode/{currencyCode}")
    public List<Account> getAllAccountsByCurrencyCode(@PathVariable String currencyCode) {
        CurrencyCode currencyCode1 = CurrencyCode.valueOf(currencyCode.toUpperCase());
        return accounts.stream().filter(account -> account.getCurrencyCode() == currencyCode1).toList();
    }

    @GetMapping("/search")
    public List<Account> getAllAccountsByCurrencyCodeBalance(@RequestParam BigDecimal minValue, @RequestParam BigDecimal maxValue) {
        return accounts.stream()
                .filter(account -> account.getBalance().compareTo(minValue) >= 0 && account.getBalance().compareTo(maxValue) <= 0)
                .toList();
    }

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return service.create(account);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount (@PathVariable long id, @RequestBody Account account) {
        return service.update(id, account).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Account> partiallyUpdateAccount(@PathVariable Long id, @RequestBody Account account) {
        return service.partialUpdate(id, account)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
