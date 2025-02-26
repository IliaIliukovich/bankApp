package de.telran.bankapp.service;

import de.telran.bankapp.entity.AppUser;
import de.telran.bankapp.exception.ResourceNotFoundException;
import de.telran.bankapp.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {
    private final AppUserRepository repository;

    public AppUserService(AppUserRepository repository) {
        this.repository = repository;
    }

    public List<AppUser> getAllUsers() {
        return repository.findAll();
    }

    public AppUser getUserById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public AppUser createUser(AppUser user) {
        return repository.save(user);
    }

    public void deleteUser(String id) {
        repository.deleteById(id);
    }

    public AppUser updateUser(String id, AppUser userDetails) {
        AppUser user = getUserById(id);
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setRole(userDetails.getRole());
        return repository.save(user);
    }

    public AppUser patchUser(String id, AppUser userDetails) {
        AppUser user = getUserById(id);
        if (userDetails.getEmail() != null) user.setEmail(userDetails.getEmail());
        if (userDetails.getPassword() != null) user.setPassword(userDetails.getPassword());
        if (userDetails.getRole() != null) user.setRole(userDetails.getRole());
        return repository.save(user);
    }
}

