package de.telran.bankapp.service;

import de.telran.bankapp.entity.Client;
import de.telran.bankapp.entity.enums.ClientStatus;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
import de.telran.bankapp.repository.ClientRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@Slf4j
public class ClientService {

    private static Logger logger = LogManager.getLogger(ClientService.class);

    private ClientRepository repository;

    @Autowired
    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public List<Client> getAll() {
        List<Client> clients = repository.findAll();
//        if (log.isDebugEnabled()) {
//            log.debug("Clients retrieved from db");
////        log.debug("Client ids: " + clients.stream().map(Client::getId).toList());
//            log.debug("Client ids: {}", clients.stream().map(Client::getId).toList());
//        }
        logger.debug("Clients retrieved from db");
        logger.debug("Client ids: {}", () -> clients.stream().map(Client::getId).toList());
        return clients;
    }

    public Optional<Client> getClientById(String uuid) {
        return repository.findById(uuid);
    }

    public List<Client> findByName(String name) {
        return repository.findByFirstName(name);
    }

    public List<Client> searchBySurnameAndAddress(String surname, String address) {
        return repository.nativeQuery(surname, address);
    }

    public Client addClient(Client client) {
       return repository.save(client);
    }

    public Client updateClient(Client client) {
        String id = client.getId();
        Optional<Client> optional = repository.findById(id);
        if (optional.isPresent()) {
            return repository.save(client);
        }
        throw new BankAppResourceNotFoundException("Client with id = " + id + " not found in database");
    }

    public Client updateAddress(String id, String address) {
        Optional<Client> optional = repository.findById(id);
        if (optional.isPresent()) {
            Client client = optional.get();
            client.setAddress(address);
            Client saved = repository.save(client);
            return saved;
        }
        throw new BankAppResourceNotFoundException("Client with id = " + id + " not found in database");
    }

    public void changeStatus(String id, ClientStatus status) {
        ClientStatus clientStatus = status == null ? ClientStatus.ACTIVE : status;
        int updated = repository.updateStatus(id, clientStatus);
        if (updated == 0) throw new BankAppResourceNotFoundException("Client with id = " + id + " not found in database");
    }

    public void deleteClient(String id) {
        repository.deleteById(id);
    }

}
