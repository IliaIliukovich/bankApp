package de.telran.bankapp.service;

import de.telran.bankapp.entity.Account;
import de.telran.bankapp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private AccountRepository repository;

    @Autowired
    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public List<Account> getAll() {
        return repository.findAll();
    }

    public ResponseEntity<Account> create(Account account) {
        return ResponseEntity.ok(repository.save(account));

    }
    public Optional<Account> update(Long id, Account updatedAccount) {
        return repository.findById(id).map(existingAccount -> {
            existingAccount.setBalance(updatedAccount.getBalance());
            existingAccount.setCurrencyCode(updatedAccount.getCurrencyCode());
            existingAccount.setStatus(updatedAccount.getStatus());
            existingAccount.setType(updatedAccount.getType());

            return repository.save((Account) existingAccount);
        });
    }

    public Optional<Account> partialUpdate(Long id, Account updatedAccount) {
        return repository.findById(id).map(existingAccount -> {
            if (updatedAccount.getBalance() != null) {
                existingAccount = updatedAccount.getBalance();
            }
            if (updatedAccount.getCurrencyCode() != null) {
                existingAccount = updatedAccount.getCurrencyCode();
            }
            if (updatedAccount.getStatus() != null) {
                existingAccount = updatedAccount.getStatus();
            }
            if (updatedAccount.getType() != null) {
                existingAccount = updatedAccount.getType();
            }
            assert existingAccount instanceof Account;
            return repository.save((Account) existingAccount);
        });
    }

    public boolean delete(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.delete(id);
            return true;
        }
        return false;
    }




