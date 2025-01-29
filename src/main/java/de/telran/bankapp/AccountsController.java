package de.telran.bankapp;


import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountsService accountsService;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountsService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public Optional<Account> getAccountById(@PathVariable Long id) {
        return accountsService.getAccountById(id);
    }

    @GetMapping("/currency/{currency}")
    public List<Account> getAccountsByCurrency(@PathVariable CurrencyCode currency) {
        return accountsService.getAccountsByCurrency(currency);
    }

    @GetMapping("/balance")
    public List<Account> getAccountsByBalanceRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return accountsService.getAccountsByBalanceRange(min,max);
    }

}


