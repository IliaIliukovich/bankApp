package de.telran.bankapp.controller;

import de.telran.bankapp.dto.AppUserDto;
import de.telran.bankapp.dto.AppUserRegisterDto;
import de.telran.bankapp.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @GetMapping("/all")
    public List<AppUserDto> getAllUsers() {
        return service.getAllUsers();
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public AppUserDto getUserById(@PathVariable String id) {
        return service.getUserById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @PostMapping
    public AppUserDto createUser(@Valid @RequestBody AppUserDto user) {
        return service.createUser(user);
    }

    @PostMapping("/register")
    public AppUserDto registerUser(@Valid @RequestBody AppUserRegisterDto user) {
        return service.register(user);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        service.deleteUser(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @PutMapping
    public AppUserDto updateUser(@Valid @RequestBody AppUserDto userDetails) {
        return service.updateUser(userDetails);
    }

}

