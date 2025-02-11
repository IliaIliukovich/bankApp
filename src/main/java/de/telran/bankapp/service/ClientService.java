package de.telran.bankapp.service;

import de.telran.bankapp.entity.Client;
import de.telran.bankapp.entity.enums.ClientStatus;
import de.telran.bankapp.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private ClientRepository repository;

    @Autowired
    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public List<Client> getAll() {
        return repository.findAll();
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

    public Optional<Client> updateClient(Client client) {
        String id = client.getId();
        Optional<Client> optional = repository.findById(id);
        if (optional.isPresent()) {
            Client saved = repository.save(client);
            return Optional.of(saved);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Client> changeStatus(String id, String status) {
        Optional<Client> optional = repository.findById(id);
        if (optional.isPresent()) {
            Client client = optional.get();
            ClientStatus clientStatus = status == null ? ClientStatus.ACTIVE : ClientStatus.valueOf(status);
            client.setStatus(clientStatus);
            Client saved = repository.save(client);
            return Optional.of(saved);
        }
        return Optional.empty();
    }

    public void deleteClient(String id) {
        repository.deleteById(id);
    }

}
