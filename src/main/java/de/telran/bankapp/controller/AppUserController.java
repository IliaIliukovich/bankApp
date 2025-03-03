package de.telran.bankapp.controller;

import de.telran.bankapp.dto.AppUserDto;
import de.telran.bankapp.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AppUserController {

    private final AppUserService service;

    @Autowired
    public AppUserController(AppUserService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<AppUserDto> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public AppUserDto getUserById(@PathVariable String id) {
        return service.getUserById(id);
    }

    @PostMapping
    public AppUserDto createUser(@Valid @RequestBody AppUserDto user) {
        return service.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        service.deleteUser(id);
    }

    @PutMapping
    public AppUserDto updateUser(@Valid @RequestBody AppUserDto userDetails) {
        return service.updateUser(userDetails);
    }

}

