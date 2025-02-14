package de.telran.bankapp.service;

import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.Agreement;
import de.telran.bankapp.entity.Client;
import de.telran.bankapp.entity.Product;
import de.telran.bankapp.entity.enums.AccountStatus;
import de.telran.bankapp.entity.enums.AccountType;
import de.telran.bankapp.entity.enums.AgreementStatus;
import de.telran.bankapp.entity.enums.CurrencyCode;
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
        Optional<Product> product = productService.getProductById(productId);
        Optional<Client> optionalClient = clientService.getClientById(clientId);
        Account account = null;
        if (optionalClient.isPresent()) {
            if (product.isPresent()) {
                Product product1 = product.get();
                account = new Account(null, "DE88370400440532013789", AccountType.CHECKING,
                        AccountStatus.ACTIVE, initialAmount, product1.getCurrencyCode(), clientId);

                Account saved = repository.save(account);
                Agreement agreement = new Agreement(null, product1.getInterestRate(), AgreementStatus.ACTIVE,
                        product1.getLimitAmount(), saved.getId(), productId);
                agreementRepository.save(agreement);
                return saved;
            } else {

            }
        }
        return null;
    }
}




