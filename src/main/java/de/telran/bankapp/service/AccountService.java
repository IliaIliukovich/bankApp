package de.telran.bankapp.service;

import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.Agreement;
import de.telran.bankapp.entity.Client;
import de.telran.bankapp.entity.Product;
import de.telran.bankapp.entity.enums.*;
import de.telran.bankapp.exception.BankAppBadRequestException;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
import de.telran.bankapp.repository.AccountRepository;
import de.telran.bankapp.repository.AgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository repository;
    private final ProductService productService;

    private ClientService clientService;
    private AgreementRepository agreementRepository;

    @Autowired
    public AccountService(AccountRepository repository, ProductService productService, AgreementRepository agreementRepository, ClientService clientService) {
        this.repository = repository;
        this.productService = productService;
        this.agreementRepository = agreementRepository;
        this.clientService = clientService;
    }

    public Account getAccountById(Long id) {
        Optional<Account> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new BankAppResourceNotFoundException("Account with id = " + id + " not found in database");
    }

    public List<Account> getAll() {
        return repository.findAll();
    }

    public List<Account> getAllAccountsByCurrencyCode(CurrencyCode currencyCode) {
        return repository.getAllAccountsByCurrencyCode(currencyCode);
    }

    public List<Account> getAllAccountsByBalance(BigDecimal minValue, BigDecimal maxValue) {
        return repository.getAllAccountsByBalance(minValue, maxValue);
    }
    @Transactional
    public Account create(Account account) {
        return repository.save(account);
    }

    @Transactional
    public Account updateAccount(Account account) {
        Long id = account.getId();
        Optional<Account> accountOptional = repository.findById(id);
        if (accountOptional.isPresent()) {
            return repository.save(account);
        }
        throw new BankAppResourceNotFoundException("Account with id = " + id + " not found in database");
    }

    @Transactional
    public void deleteAccount(Long id) {
        repository.deleteAccountById(id);
    }


    @Transactional
    public Account createNewAccount(String clientId, Long productId, BigDecimal initialAmount) {
        Optional<Product> productOptional = productService.getProductById(productId);
        Optional<Client> optionalClient = clientService.getClientById(clientId);
        if (!optionalClient.isPresent() || !productOptional.isPresent()) {
            throw new BankAppResourceNotFoundException("Client with id = " + clientId + " or product with id " + productId+ " not found in database");
        }
        Product product = productOptional.get();
        if (product.getStatus() == ProductStatus.INACTIVE) {
            throw new BankAppBadRequestException("The product status is INACTIVE");
        }
        if (initialAmount.compareTo(product.getLimitAmount()) > 0) {
            throw new BankAppBadRequestException("InitialAmount is more than product limitAmount");
        }
        String accountName = createNewAccountName();
        Account account = new Account(null, accountName, AccountType.CHECKING,
                AccountStatus.ACTIVE, initialAmount, product.getCurrencyCode(), clientId);

        Account savedAccount = repository.save(account);
        Agreement agreement = new Agreement(null, product.getInterestRate(), AgreementStatus.ACTIVE,
                product.getLimitAmount(), savedAccount.getId(), productId);
        agreementRepository.save(agreement);
        return savedAccount;
    }


    private String createNewAccountName() {
        Random random = new Random();
        Integer randomNumber = 10_000_000 + random.nextInt(90_000_000);
        return "DE883704004400" + randomNumber;
    }
}




