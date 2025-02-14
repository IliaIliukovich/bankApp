package de.telran.bankapp.controller;

import de.telran.bankapp.entity.Client;
import de.telran.bankapp.entity.enums.ClientStatus;
import de.telran.bankapp.service.ClientService;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(("/client"))
@Validated
public class ClientController {

    private ClientService service;

    @Autowired
    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<Client> getAll() {
        return service.getAll();
    }

    @GetMapping("{uuid}")
    public Optional<Client> getClientById(@PathVariable String uuid) {
        return service.getClientById(uuid);
    }

    @GetMapping("/search")
    public List<Client> findByName(@RequestParam String name) {
        return service.findByName(name);
    }

    @GetMapping("/searchBySurnameAndAddress")
    public List<Client> findByName(@RequestParam String surname, @RequestParam String address) {
        return service.searchBySurnameAndAddress(surname, address);
    }

    @PostMapping
    public ResponseEntity<Client> addClient(@RequestBody @Valid Client client) {
        Client created = service.addClient(client);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Client> updateClient(@RequestBody @Valid Client client) {
        Client updated = service.updateClient(client);
        return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
    }

    @PatchMapping
    public ResponseEntity<Void> changeStatus(@RequestParam String id, @RequestParam(required = false) ClientStatus status){
        service.changeStatus(id, status);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PatchMapping("/changeAddress")
    public ResponseEntity<Client> changeAddress(@RequestParam String id,
                                                @RequestParam @Length(max = 150, message ="{validation.client.address}")
                                                String address){
        Client client = service.updateAddress(id, address);
        return new ResponseEntity<>(client, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable String id) {
        service.deleteClient(id);
        return ResponseEntity.accepted().build();
    }

}
