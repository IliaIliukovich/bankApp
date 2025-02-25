package de.telran.bankapp.service;

import de.telran.bankapp.dto.AccountCreateDto;
import de.telran.bankapp.dto.AccountDto;
import de.telran.bankapp.dto.AccountPostCreateDto;
import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.Agreement;
import de.telran.bankapp.entity.Client;
import de.telran.bankapp.entity.Product;
import de.telran.bankapp.entity.enums.AgreementStatus;
import de.telran.bankapp.entity.enums.CurrencyCode;
import de.telran.bankapp.entity.enums.ProductStatus;
import de.telran.bankapp.exception.BankAppBadRequestException;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
import de.telran.bankapp.mapper.AccountMapper;
import de.telran.bankapp.repository.AccountRepository;
import de.telran.bankapp.repository.AgreementRepository;
import de.telran.bankapp.repository.ClientRepository;
import de.telran.bankapp.repository.ProductRepository;
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
    private final ClientRepository clientRepository;
    private final AgreementRepository agreementRepository;
    private final AccountMapper mapper;
    private final ProductRepository productRepository;

    @Autowired
    public AccountService(AccountRepository repository,
                          ClientRepository clientRepository,
                          AgreementRepository agreementRepository,
                          AccountMapper mapper, ProductRepository productRepository) {
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.agreementRepository = agreementRepository;
        this.mapper = mapper;
        this.productRepository = productRepository;
    }

    public AccountDto getAccountById(Long id) {
        Optional<Account> optional = repository.findById(id);
        if (optional.isPresent()) {
            return mapper.entityToDto(optional.get());
        }
        throw new BankAppResourceNotFoundException("Account with id = " + id + " not found in database");
    }

    public List<AccountDto> getAll() {
        return mapper.entityListToDto(repository.findAll());
    }

    public List<AccountDto> getAllAccountsByCurrencyCode(CurrencyCode currencyCode) {
        return mapper.entityListToDto(repository.getAllAccountsByCurrencyCode(currencyCode));
    }

    public List<AccountDto> getAllAccountsByBalance(BigDecimal minValue, BigDecimal maxValue) {
        return mapper.entityListToDto(repository.getAllAccountsByBalance(minValue, maxValue));
    }
    @Transactional
    public AccountDto create(AccountPostCreateDto dto) {
        Account account = mapper.createPostDtoToEntity(dto);
        return mapper.entityToDto(repository.save(account));
    }

    @Transactional
    public AccountDto updateAccount(AccountDto dto) {

        Long id = dto.getId();

        Optional<Account> accountOptional = repository.findById(id);
        if (accountOptional.isPresent()) {
            Account account = mapper.dtoToEntity(dto);
            return mapper.entityToDto(repository.save(account));
        }
        throw new BankAppResourceNotFoundException("Account with id = " + id + " not found in database");
    }

    @Transactional
    public void deleteAccount(Long id) {
        repository.deleteAccountById(id);
    }


    @Transactional
    public Account createNewAccount(AccountCreateDto dto) {
        Optional<Product> productOptional = productRepository.findById(dto.getProductId());
        Optional<Client> optionalClient = clientRepository.findById(dto.getClientId());

        validateAccountDto(dto, optionalClient, productOptional);

        Product product = productOptional.get();

//        Account newAccount = Account.builder().name("Name").status(AccountStatus.ACTIVE).id(123L).build();

        Account account = mapper.createDtoToEntity(dto);
        account.setName(createNewAccountName());
        account.setCurrencyCode(product.getCurrencyCode());
        Account savedAccount = repository.save(account);

        Agreement agreement = new Agreement(null, product.getInterestRate(), AgreementStatus.ACTIVE,
                product.getLimitAmount(), savedAccount.getId(), dto.getProductId());
        agreementRepository.save(agreement);
        return savedAccount;
    }

    private static void validateAccountDto(AccountCreateDto dto,
                                           Optional<Client> optionalClient,
                                           Optional<Product> productOptional) {
        if (optionalClient.isEmpty() || productOptional.isEmpty()) {
            throw new BankAppResourceNotFoundException("Client with id = " + dto.getClientId() + " or product with id " + dto.getProductId() + " not found in database");
        }
        if (productOptional.get().getStatus() == ProductStatus.INACTIVE) {
            throw new BankAppBadRequestException("The product status is INACTIVE");
        }
        if (dto.getInitialAmount().compareTo(productOptional.get().getLimitAmount()) > 0) {
            throw new BankAppBadRequestException("InitialAmount is more than product limitAmount");
        }
    }


    private String createNewAccountName() {
        Random random = new Random();
        int randomNumber = 10_000_000 + random.nextInt(90_000_000);
        return "DE883704004400" + randomNumber;
    }
}




