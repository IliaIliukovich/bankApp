package de.telran.bankapp.controller;

import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.Client;
import de.telran.bankapp.entity.Manager;
import de.telran.bankapp.entity.enums.ManagerStatus;
import de.telran.bankapp.service.ManagerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.CodePointLength;
import org.hibernate.validator.constraints.Length;
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
    public Optional<Manager> getManagerById(@PathVariable Long id) {
        return Optional.ofNullable(service.getManagerById(id));
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
        Manager updatedManager = service.updateManager(manager);
        return new ResponseEntity<>(updatedManager,HttpStatus.ACCEPTED);
    }

    @GetMapping("/searchByFirstAndLastName")
    public List<Manager> findByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName){
        return service.findByFirstNameAndLastName(firstName,lastName);
    }

    @GetMapping("/searchByFirstLetterFromFirstNameAndFirstLetterFromLastName")
    public List<Manager> findFirstLetterFromFirstNameAndFirstLetterFromLastName(@RequestParam String firstName, @RequestParam String lastName){
        return service.findFirstLetterFromFirstNameAndFirstLetterFromLastName(firstName,lastName);
    }

    @PatchMapping
    public ResponseEntity<Void> changeManagerStatus(@RequestParam Long id, @RequestParam(required = false) ManagerStatus status) {
        service.changeManagerStatus(id,status);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Long id) {
        service.deleteManager(id);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/changeLastName")
    public ResponseEntity<Manager> changeLastName(
            @RequestParam Long id,
            @RequestParam
            @Valid
            @Pattern(
                    regexp = "^[A-ZÜÄÖ][a-zA-Züäö]{0,44}$",
                    message = "Last name should not be null," +
                            " should be in latin letters," +
                            " first letter shuld be capital," +
                            " shuld have only" +
                            " - ' üäö  and no more than 45 symbols!"
            ) String newLastName) {
        Manager managerWithNewLastName = service.updateLastName(id, newLastName);
        return new ResponseEntity<>(managerWithNewLastName, HttpStatus.ACCEPTED);
    }


}
