package de.telran.bankapp.service;

import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.enums.CurrencyCode;
import de.telran.bankapp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private AccountRepository repository;

    @Autowired
    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Optional<Account> getAccountById(Long id) {
        return repository.findById(id);
    }

    public List<Account> getAll() {
        return repository.findAll();
    }

    public List<Account> getAllAccountsByCurrencyCode(String currencyCode) {
        return repository.getAllAccountsByCurrencyCode(currencyCode);
    }

    public List<Account> getAllAccountsByCurrencyCodeBalance(BigDecimal minValue, BigDecimal maxValue) {
        return repository.getAllAccountsByCurrencyCodeBalance(minValue, maxValue);
    }

    public Account create(Account account) {
        return repository.create(account);

    }

    public Account update(Account updatedAccount) {
        return repository.update(updatedAccount);

    }

    public void deleteAccount(Long id) {
       repository.delete(id);
    }
}




