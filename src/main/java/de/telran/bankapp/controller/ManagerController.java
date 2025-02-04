package de.telran.bankapp.controller;

import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.Client;
import de.telran.bankapp.entity.Manager;
import de.telran.bankapp.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    private ManagerService service;

    @Autowired
    public ManagerController(ManagerService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<Manager> getAllManagers() {
        return service.getAllManagers();
    }

    @PostMapping
    public ResponseEntity<Manager> addManager(@RequestBody Manager manager) {
        Manager created = service.addManager(manager);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Manager> updateManager(@RequestBody Manager manager) {
        Optional<Manager> updated = service.updateManager(manager);
        if (updated.isPresent()) {
            return new ResponseEntity<>(updated.get(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("{id}")
    public ResponseEntity<Manager> getManagerById(@PathVariable Long id) {
        Optional<Manager> found = service.getManagerById(id);
        if (found.isPresent()) {
            return new ResponseEntity<>(found.get(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping
    public ResponseEntity<Manager> changeManagerStatus(@RequestParam Long id, @RequestParam(required = false) String status) {
        Optional<Manager> found = service.changeManagerStatus(id, status);
        if (found.isPresent()) {
            return new ResponseEntity<>(found.get(), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping({"id"})
    public ResponseEntity<Void> deleteManager(@PathVariable Long id) {
        if (service.deleteManager(id)) {
            System.out.println("Manager with id = " + id + " is sucsesfully delete");
        } else {
            System.out.println("Error! No manager was delete!");
        }
        return ResponseEntity.accepted().build();
    }

}
