package de.telran.bankapp.service;

import de.telran.bankapp.dto.ClientAccountStatisticsDto;
import de.telran.bankapp.dto.ClientCreateDto;
import de.telran.bankapp.dto.ClientDto;
import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.Client;
import de.telran.bankapp.entity.Manager;
import de.telran.bankapp.entity.Transaction;
import de.telran.bankapp.entity.enums.ClientStatus;
import de.telran.bankapp.entity.enums.CurrencyCode;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
import de.telran.bankapp.mapper.ClientMapper;
import de.telran.bankapp.repository.ClientRepository;
import de.telran.bankapp.repository.ManagerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
//@Slf4j
@Transactional(readOnly = true)
public class ClientService {

    private static Logger logger = LogManager.getLogger(ClientService.class);

    private ClientRepository repository;
    private ManagerRepository managerRepository;
    private ClientMapper mapper;

    @Autowired
    public ClientService(ClientRepository repository, ManagerRepository managerRepository, ClientMapper mapper) {
        this.repository = repository;
        this.managerRepository = managerRepository;
        this.mapper = mapper;
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

    public Optional<ClientDto> getClientById(String uuid) {
        Optional<Client> client = repository.findById(uuid);
        ClientDto dto = mapper.entityToDto(client.orElse(null));
        return Optional.ofNullable(dto);
    }

    public List<ClientDto> findByName(String name) {
        List<Client> clients = repository.findByFirstName(name);
        return mapper.entityListToDto(clients);
    }

    public List<ClientDto> searchBySurnameAndAddress(String surname, String address) {
        List<Client> clients = repository.nativeQuery(surname, address);
        return mapper.entityListToDto(clients);
    }

    @Transactional
    public ClientDto addClient(ClientCreateDto dto) {
        Client client = mapper.createDtoToEntity(dto);
        if (dto.getManagerId() != null) {
//            Manager manager = managerRepository.findById(dto.getManagerId()).orElse(null);
            Manager manager = managerRepository.getReferenceById(dto.getManagerId()); // lazy loading
            client.setManager(manager);
        }
        Client saved = repository.save(client);
        return mapper.entityToDto(saved);
    }

    @Transactional
    public ClientDto updateClient(ClientDto dto) {
        String id = dto.getId();
        Optional<Client> optional = repository.findById(id);
        if (optional.isPresent()) {
            Client client = mapper.dtoToEntity(dto);
            if (dto.getManagerId() != null) {
                Manager manager = managerRepository.getReferenceById(dto.getManagerId()); // lazy loading
                client.setManager(manager);
            }
            Client saved = repository.save(client);
            return mapper.entityToDto(saved);
        }
        throw new BankAppResourceNotFoundException("Client with id = " + id + " not found in database");
    }

    @Transactional
    public ClientDto updateAddress(String id, String address) {
        Optional<Client> optional = repository.findById(id);
        if (optional.isPresent()) {
            Client client = optional.get();
            client.setAddress(address);
            Client saved = repository.save(client);
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

    public ClientAccountStatisticsDto getSummaryInfo(String uuid) {

        Client client = repository.findById(uuid)
                .orElseThrow(() -> new BankAppResourceNotFoundException("Client with id = " + uuid + " not found in database"));

        List<Account> accounts = client.getAccounts();

        Map<CurrencyCode, BigDecimal> balanceByCurrency = accounts.stream()
                .collect(Collectors.groupingBy(Account::getCurrencyCode,
                        Collectors.mapping(Account::getBalance, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

        Map<CurrencyCode, BigDecimal> incomesByCurrencyCode = accounts.stream()
                .flatMap(account -> account.getDebitTransactions().stream()
                        .filter(t -> t.getDebitAccount().getId().equals(account.getId()) && !t.getCreditAccount().getClient().equals(client)))
                .collect(Collectors.groupingBy(t -> t.getDebitAccount().getCurrencyCode(),
                        Collectors.mapping(Transaction::getAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

        Map<CurrencyCode, BigDecimal> expensesByCurrencyCode = accounts.stream()
                .flatMap(account -> account.getCreditTransactions().stream()
                        .filter(t -> t.getCreditAccount().getId().equals(account.getId()) && !t.getDebitAccount().getClient().equals(client)))
                .collect(Collectors.groupingBy(t -> t.getCreditAccount().getCurrencyCode(),
                        Collectors.mapping(Transaction::getAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

        return new ClientAccountStatisticsDto(balanceByCurrency, incomesByCurrencyCode, expensesByCurrencyCode);
    }
}
