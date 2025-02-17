package de.telran.bankapp.service;

import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.Agreement;
import de.telran.bankapp.entity.Client;
import de.telran.bankapp.entity.Product;
import de.telran.bankapp.entity.enums.*;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
import de.telran.bankapp.repository.AccountRepository;
import de.telran.bankapp.repository.AgreementRepository;
import de.telran.bankapp.repository.ClientRepository;
import de.telran.bankapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    
    private AccountRepository repository;
    private ProductRepository productrepository;

    private ClientRepository clientRepository;
    private AgreementRepository agreementRepository;

    @Autowired
    public AccountService(AccountRepository repository, ProductRepository productRepository,
                          AgreementRepository agreementRepository,ClientRepository clientRepository) {
        this.repository = repository;
        this.agreementRepository = agreementRepository;
        this.clientRepository = clientRepository;
        this.productrepository = productRepository;
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
        Optional<Product> optionalProduct = productrepository.findById(productId);
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Account account = null;
        if (optionalClient.isPresent()&& optionalProduct.isPresent()) {
            
                Product product = optionalProduct.get();
                account = new Account(null, "NewAccountName", AccountType.CHECKING,
                        AccountStatus.ACTIVE, initialAmount, product.getCurrencyCode(), clientId);

                Account saved = repository.save(account);
                Agreement agreement = new Agreement(null, product.getInterestRate(), AgreementStatus.ACTIVE,
                        product.getLimitAmount(), saved.getId(), productId);
                agreementRepository.save(agreement);
                return saved;
        } else if (optionalClient.isEmpty()) {
            throw new BankAppResourceNotFoundException("Client with id = " + clientId + " not found in database");
        } else if (optionalProduct.isEmpty()) {
            throw new BankAppResourceNotFoundException("Product with id = " + productId + " not found in database");
        } else if (optionalProduct.get().getStatus().equals(ProductStatus.INACTIVE)) {
            throw new BankAppResourceNotFoundException("Product with id = " + productId + " has status Inactive!");
        } else if (initialAmount.compareTo(optionalProduct.get().getLimitAmount()) == 1) {
            throw new BankAppResourceNotFoundException("Product with id = " + productId +
                    " has  limitAmount "+optionalProduct.get().getLimitAmount()+" les then initial Amount"
            +initialAmount+" !");
        }else {
            throw new BankAppResourceNotFoundException("New account is not created!");
        }
    }
}




