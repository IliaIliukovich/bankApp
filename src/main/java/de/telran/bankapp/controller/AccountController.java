package de.telran.bankapp.controller;

import de.telran.bankapp.entity.Account;
import de.telran.bankapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Account createAccount(@RequestBody Account account) {
        return service.create(account);
    }

    @PutMapping("/update/{id}")
    public Account updateAccount (@RequestBody Account account) {
        return service.create(account);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Long id) {
        service.deleteAccount(id);
        return "success";
    }

}
