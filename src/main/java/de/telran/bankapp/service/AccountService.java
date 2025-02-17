package de.telran.bankapp.service;

import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.Client;
import de.telran.bankapp.entity.enums.CurrencyCode;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
import de.telran.bankapp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Account getAccountById(Long id) {
        return repository.findById(id).get();
    }

    public List<Account> getAll() {
        return repository.findAll();
    }

    public List<Account> getAllAccountsByCurrencyCode(String currencyCode) {
        return repository.getAllAccountsByCurrencyCode(CurrencyCode.valueOf(currencyCode));
    }

    public List<Account> getAllAccountsByBalance(BigDecimal minValue, BigDecimal maxValue) {
        return repository.getAllAccountsByBalance(minValue, maxValue);
    }




        public Account create(Account account) {
            String id = String.valueOf(account.getId());
            Optional<Account> optional = repository.findById(Long.valueOf(id));
            if (optional.isPresent()) {
                return repository.save(account);
            }
            throw new BankAppResourceNotFoundException("Account with id = " + id + " not found in database");
        }



    public void deleteAccount(Long id) {
       repository.deleteAccountById(id);
    }



}




