package de.telran.bankapp.controller;

import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.Client;
import de.telran.bankapp.entity.Manager;
import de.telran.bankapp.entity.enums.ManagerStatus;
import de.telran.bankapp.service.ManagerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/manager")
@Validated
public class ManagerController {

    private ManagerService service;

    @Autowired
    public ManagerController(ManagerService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<Manager> getAllManagers() {
        return service.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Manager> getManagerById(@PathVariable Long id) {
        Optional<Manager> found = service.getManagerById(id);
        if (found.isPresent()) {
            return new ResponseEntity<>(found.get(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search")
    public List<Manager> findByFirstName(@RequestParam String firstName){
        return service.findByName(firstName);
    }

    @PostMapping
    public ResponseEntity<Manager> addManager(@RequestBody @Valid Manager manager) {
        Manager created = service.addManager(manager);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Manager> updateManager(@RequestBody @Valid Manager manager) {
        Optional<Manager> updated = service.updateManager(manager);
        if (updated.isPresent()) {
            return new ResponseEntity<>(updated.get(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/searshByFirstAndLastName")
    public List<Manager> findByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName){
        return service.findByFirstNameAndLastName(firstName,lastName);
    }

    @GetMapping("/searchByFirstLetterFromFirstNameAndFirstLetterFromLastName")
    public List<Manager> findFirstLetterFromFirstNameAndFirstLetterFromLastName(@RequestParam String firstName, @RequestParam String lastName){
        return service.findFirstLetterFromFirstNameAndFirstLetterFromLastName(firstName,lastName);
    }

    @PatchMapping
    public ResponseEntity<Void> changeManagerStatus(@RequestParam Long id, @RequestParam(required = false) ManagerStatus status) {
        Integer integer = service.changeManagerStatus(id,status);
        if(!integer.equals(0)){
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Long id) {
        service.deleteManager(id);
        return ResponseEntity.accepted().build();
    }

}
