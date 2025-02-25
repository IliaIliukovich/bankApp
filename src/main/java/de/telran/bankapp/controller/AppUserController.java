package de.telran.bankapp.controller;

import de.telran.bankapp.entity.AppUser;
import de.telran.bankapp.entity.enums.UserRole;
import de.telran.bankapp.repository.AppUserRepository;
import de.telran.bankapp.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class AppUserController {

    
    private final AppUserService appUserService;
    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public ResponseEntity<List<AppUser>> getAllUsers() {
        return ResponseEntity.ok(appUserService.getAll());
    }

    @GetMapping("/{id}")
    public Optional<AppUser> getUserById(@PathVariable String id) {
        return appUserService.getAppUserById(id);
    }

    @PostMapping
    public ResponseEntity<AppUser> createUser(@RequestBody AppUser user) {
        AppUser created = appUserService.addAppUser(user);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<AppUser> updateAppUser(@RequestBody AppUser user) {
        if (user.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<AppUser> updated = appUserService.updateAppUser(user);
        if (updated.isPresent()) {
            return new ResponseEntity<>(updated.get(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateAppUserRole(@PathVariable String id, @RequestParam(required = false) UserRole role) {
        boolean updated = appUserService.changeRole(id, role);

        if (!updated) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User role updated successfully");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        appUserService.deleteAppUser(id);
        return ResponseEntity.noContent().build();
    }
}