package de.telran.bankapp.controller;

import de.telran.bankapp.entity.AppUser;
import de.telran.bankapp.service.AppUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
class AppUserController {
    private final AppUserService service;

    public AppUserController(AppUserService service) {
        this.service = service;
    }

    @GetMapping
    public List<AppUser> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public AppUser getUserById(@PathVariable String id) {
        return service.getUserById(id);
    }

    @PostMapping
    public AppUser createUser(@RequestBody AppUser user) {
        return service.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        service.deleteUser(id);
    }

    @PutMapping("/{id}")
    public AppUser updateUser(@PathVariable String id, @RequestBody AppUser userDetails) {
        return service.updateUser(id, userDetails);
    }

    @PatchMapping("/{id}")
    public AppUser patchUser(@PathVariable String id, @RequestBody AppUser userDetails) {
        return service.patchUser(id, userDetails);
    }
}
