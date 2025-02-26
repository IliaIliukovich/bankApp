package de.telran.bankapp.controller;

import de.telran.bankapp.entity.AppUser;
import de.telran.bankapp.exception.ResourceNotFoundException;
import de.telran.bankapp.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public AppUser createUser(@Valid @RequestBody AppUser user) {
        return service.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        service.deleteUser(id);
    }

    @PutMapping("/{id}")
    public AppUser updateUser(@PathVariable String id, @Valid @RequestBody AppUser userDetails) {
        return service.updateUser(id, userDetails);
    }

    @PatchMapping("/{id}")
    public AppUser patchUser(@PathVariable String id, @Valid @RequestBody AppUser userDetails) {
        return service.patchUser(id, userDetails);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage(),
                        (existing, replacement) -> existing
                ));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}

