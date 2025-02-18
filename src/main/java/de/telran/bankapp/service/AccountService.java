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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private AccountRepository repository;
    private ProductService productService;

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
        return repository.save(account);

    }

    public void deleteAccount(Long id) {
        repository.deleteAccountById(id);
    }


    public Account createNewAccount(String clientId, Long productId, BigDecimal initialAmount) {
        Optional<Product> productOptional = productService.getProductById(productId);
        Optional<Client> optionalClient = clientService.getClientById(clientId);
        if (!optionalClient.isPresent()) {
            throw new BankAppResourceNotFoundException("Client with id = " + clientId + " not found in database");
        }
        if (!productOptional.isPresent()) {
            throw new BankAppResourceNotFoundException("Product with id = " + productId + " not found in database");
        }
        Product product = productOptional.get();
        if (product.getStatus() == ProductStatus.INACTIVE) {
            throw new BankAppBadRequestException("The product status is INACTIVE");
        }
        if (initialAmount.compareTo(product.getLimitAmount()) > 0) {
            throw new BankAppBadRequestException("InitialAmount is more than product limitAmount");
        }
        Account account = new Account(null, "DE88370400440532013789", AccountType.CHECKING,
                AccountStatus.ACTIVE, initialAmount, product.getCurrencyCode(), clientId);

        Account savedAccount = repository.save(account);
        Agreement agreement = new Agreement(null, product.getInterestRate(), AgreementStatus.ACTIVE,
                product.getLimitAmount(), savedAccount.getId(), productId);
        agreementRepository.save(agreement);
        return savedAccount;
    }
}




