package de.telran.bankapp.service;

import de.telran.bankapp.dto.ClientAccountStatisticsDto;
import de.telran.bankapp.dto.ClientCreateDto;
import de.telran.bankapp.dto.ClientDto;
import de.telran.bankapp.entity.*;
import de.telran.bankapp.entity.enums.ClientStatus;
import de.telran.bankapp.entity.enums.CurrencyCode;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
import de.telran.bankapp.mapper.ClientMapper;
import de.telran.bankapp.repository.ClientRepository;
import de.telran.bankapp.repository.ManagerRepository;
import de.telran.bankapp.security.AuthService;
import de.telran.bankapp.security.JwtAuthentication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@Slf4j
@Transactional(readOnly = true)
public class ClientService {

    private static Logger logger = LogManager.getLogger(ClientService.class);

    private final ClientRepository repository;
    private final ManagerRepository managerRepository;
    private final ClientMapper mapper;
    private final AuthService authService;
    private final AppUserService appUserService;
    private final CurrencyService currencyService;

    @Autowired
    public ClientService(ClientRepository repository, ManagerRepository managerRepository, ClientMapper mapper, AuthService authService, AppUserService appUserService, CurrencyService currencyService) {
        this.repository = repository;
        this.managerRepository = managerRepository;
        this.mapper = mapper;
        this.authService = authService;
        this.appUserService = appUserService;
        this.currencyService = currencyService;
    }


    public List<ClientDto> getAll() {
        List<Client> clients = repository.findAll();
//        if (log.isDebugEnabled()) {
//            log.debug("Clients retrieved from db");
//            log.debug("Client ids: " + clients.stream().map(Client::getId).toList());
//            log.debug("Client ids: {}", clients.stream().map(Client::getId).toList());
//        }
        logger.debug("Clients retrieved from db");
        logger.debug("Client ids: {}", () -> clients.stream().map(Client::getId).toList());
        return mapper.entityListToDto(clients);
    }

    public List<ClientDto> getAllSorted(Sort sort) {
        List<Client> clientList = repository.findAll(sort);
        return mapper.entityListToDto(clientList);

    }

    public Optional<ClientDto> getClientById(String uuid) {
        Optional<Client> client = repository.findById(uuid);
        ClientDto dto = mapper.entityToDto(client.orElse(null));
        return Optional.ofNullable(dto);
    }

    public List<ClientDto> findByName(String name, Sort sort) {
        List<Client> clients = repository.findByFirstName(name, sort);
        return mapper.entityListToDto(clients);
    }

    public List<ClientDto> searchBySurnameAndAddress(String surname, String address) {
        List<Client> clients = repository.nativeQuery(surname, address);
        return mapper.entityListToDto(clients);
    }

    @Transactional
    public ClientDto addClient(ClientCreateDto dto) {
        Client client = mapper.createDtoToEntity(dto); // transient
        if (dto.getManagerId() != null) {
//            Manager manager = managerRepository.findById(dto.getManagerId()).orElse(null);
            Manager manager = managerRepository.getReferenceById(dto.getManagerId()); // lazy loading
            client.setManager(manager);
        }
        Client saved = repository.save(client); // persistent
        return mapper.entityToDto(saved);
    }

    @Transactional
    public ClientDto updateClient(ClientDto dto) {
        String id = dto.getId();
        Optional<Client> optional = repository.findById(id); // persistent
        if (optional.isPresent()) {
            Client client = mapper.dtoToEntity(dto); // transient
            if (dto.getManagerId() != null) {
                Manager manager = managerRepository.getReferenceById(dto.getManagerId()); // lazy loading
                client.setManager(manager);
            }
            Client saved = repository.save(client); // merge client from db with client from dto ----> persistent
            return mapper.entityToDto(saved);
        }
        throw new BankAppResourceNotFoundException("Client with id = " + id + " not found in database");
    }

    @Transactional
    public ClientDto updateAddress(String id, String address) {
        Optional<Client> optional = repository.findById(id); // persistent
        if (optional.isPresent()) {
            Client client = optional.get();
            client.setAddress(address);
            Client saved = repository.save(client); // unnecessary
            return mapper.entityToDto(saved);
        }
        throw new BankAppResourceNotFoundException("Client with id = " + id + " not found in database");
    }

    @Transactional
    public void changeStatus(String id, ClientStatus status) {
        ClientStatus clientStatus = status == null ? ClientStatus.ACTIVE : status;
        int updated = repository.updateStatus(id, clientStatus);
        if (updated == 0)
            throw new BankAppResourceNotFoundException("Client with id = " + id + " not found in database");
    }

    @Transactional
    public void deleteClient(String id) {
        repository.deleteById(id);
    }

    public ClientAccountStatisticsDto getSummaryInfo() {
        JwtAuthentication authentication = authService.getAuthInfo();
        String login = authentication.getLogin();
        AppUser user = appUserService.getByLogin(login).get();

        Client client = user.getClient();

        Map<CurrencyCode, BigDecimal> balanceByCurrency = getBalanceByCurrency(client);
        Map<CurrencyCode, BigDecimal> incomesByCurrencyCode = getIncomesByCurrencyCode(client);
        Map<CurrencyCode, BigDecimal> expensesByCurrencyCode = getExpensesByCurrencyCode(client);
        BigDecimal totalSum = getTotalSum(client);

        return new ClientAccountStatisticsDto(totalSum, balanceByCurrency, incomesByCurrencyCode, expensesByCurrencyCode);
    }

    private BigDecimal getTotalSum(Client client) {
        Map<String, BigDecimal> rates = currencyService.getRates();
        return client.getAccounts().stream()
                .map(a -> currencyService.convertAmountToRequiredCurrency(
                        a.getBalance(),
                        a.getCurrencyCode(),
                        CurrencyCode.USD,
                        rates)).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static Map<CurrencyCode, BigDecimal> getExpensesByCurrencyCode(Client client) {
        Map<CurrencyCode, BigDecimal> expensesByCurrencyCode = client.getAccounts().stream()
                .flatMap(account -> account.getCreditTransactions().stream()
                        .filter(t -> !t.getDebitAccount().getClient().equals(client)))
                .collect(Collectors.groupingBy(t -> t.getCreditAccount().getCurrencyCode(),
                        Collectors.mapping(Transaction::getAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        return expensesByCurrencyCode;
    }

    private static Map<CurrencyCode, BigDecimal> getIncomesByCurrencyCode(Client client) {
        Map<CurrencyCode, BigDecimal> incomesByCurrencyCode = client.getAccounts().stream()
                .flatMap(account -> account.getDebitTransactions().stream()
                        .filter(t -> !t.getCreditAccount().getClient().equals(client)))
                .collect(Collectors.groupingBy(t -> t.getDebitAccount().getCurrencyCode(),
                        Collectors.mapping(Transaction::getAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        return incomesByCurrencyCode;
    }

    private static Map<CurrencyCode, BigDecimal> getBalanceByCurrency(Client client) {
        Map<CurrencyCode, BigDecimal> balanceByCurrency = client.getAccounts().stream()
                .collect(Collectors.groupingBy(Account::getCurrencyCode,
                        Collectors.mapping(Account::getBalance, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        return balanceByCurrency;
    }
}
