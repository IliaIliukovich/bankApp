package de.telran.bankapp.controller;

import de.telran.bankapp.dto.ClientAccountStatisticsDto;
import de.telran.bankapp.dto.ClientCreateDto;
import de.telran.bankapp.dto.ClientDto;
import de.telran.bankapp.entity.enums.ClientStatus;
import de.telran.bankapp.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(("/client"))
@Validated
@Tag(name = "Client Controller", description = "REST API for managing clients in the app")
public class ClientController {

    private ClientService service;

    @Autowired
    public ClientController(ClientService service) {
        this.service = service;
    }

    @Operation(summary = "Returns all app clients")
    @GetMapping("/all")
    public List<ClientDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/allSorted")
    public List<ClientDto> getAllSorted(@SortDefault(value = "lastName") Sort sort) {
        return service.getAllSorted(sort);
    }

    @Operation(summary = "Returns a client by id")
    @GetMapping("{uuid}")
    public Optional<ClientDto> getClientById(@PathVariable String uuid) {
        return service.getClientById(uuid);
    }

    @Operation(summary = "Search by the provided name")
    @GetMapping("/search")
    public List<ClientDto> findByName(@RequestParam String name, Sort sort) {
        return service.findByName(name, sort);
    }

    @GetMapping("/searchBySurnameAndAddress")
    public List<ClientDto> findByName(@RequestParam String surname, @RequestParam String address) {
        return service.searchBySurnameAndAddress(surname, address);
    }

    @GetMapping("/summary")
    public ResponseEntity<ClientAccountStatisticsDto> getSummaryInfo(@RequestParam String uuid) {
        return new ResponseEntity<>(service.getSummaryInfo(uuid), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClientDto> addClient(@RequestBody @Valid ClientCreateDto client) {
        ClientDto created = service.addClient(client);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ClientDto> updateClient(@RequestBody @Valid ClientDto client) {
        ClientDto updated = service.updateClient(client);
        return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
    }

    @PatchMapping
    public ResponseEntity<Void> changeStatus(@RequestParam String id, @RequestParam(required = false) ClientStatus status){
        service.changeStatus(id, status);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PatchMapping("/changeAddress")
    public ResponseEntity<ClientDto> changeAddress(@RequestParam String id,
                                                @RequestParam @Length(max = 150, message ="{validation.client.address}")
                                                String address){
        ClientDto client = service.updateAddress(id, address);
        return new ResponseEntity<>(client, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable String id) {
        service.deleteClient(id);
        return ResponseEntity.accepted().build();
    }

}
