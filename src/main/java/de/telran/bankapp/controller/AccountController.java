package de.telran.bankapp.controller;

import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.Client;
import de.telran.bankapp.service.AccountService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.core.appender.rolling.action.IfAccumulatedFileCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
@Validated
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
    Account getAccountById(@PathVariable Long id) {
        return service.getAccountById(id);
    }

    @GetMapping("/currencyCode/{currencyCode}")
    public List<Account> getAllAccountsByCurrencyCode(@PathVariable String currencyCode) {
        return service.getAllAccountsByCurrencyCode(currencyCode);
    }

    @GetMapping("/search")
    public List<Account> getAllAccountsByBalance(
            @RequestParam(name = "minValue", required = false, defaultValue = "0") BigDecimal minValue,
            @RequestParam(name = "maxValue", required = false, defaultValue = "1000000") BigDecimal maxValue) {
        return service.getAllAccountsByBalance(minValue, maxValue);
    }


    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody @Valid Account account) {
        Account created = service.create(account);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Account> updateAccount (@RequestBody @Valid Account account) {
        Account updated =  service.create(account);
        return  new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Long id) {
        service.deleteAccount(id);
        return "success";
    }

}
