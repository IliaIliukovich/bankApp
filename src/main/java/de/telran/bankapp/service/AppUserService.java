package de.telran.bankapp.service;

import de.telran.bankapp.entity.AppUser;
import de.telran.bankapp.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {
    private final AppUserRepository repo;
    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(AppUserRepository repo, AppUserRepository appUserRepository) {
        this.repo = repo;
        this.appUserRepository = appUserRepository;
    }

    public List<AppUser> getAll() {
        return repo.findAll();
    }

    public Optional<AppUser> getAppUserById(String id) {
        return repo.findById(id);
    }

    public AppUser addAppUser(AppUser user) {
        return repo.save(user);
    }

    public Optional<AppUser> updateAppUser(AppUser user) {
        String id = user.getId();
        Optional<AppUser> optional = appUserRepository.findById(id);
        if (optional.isPresent()) {
            AppUser saved = appUserRepository.save(user);
            return Optional.of(saved);
        } else {
            return Optional.empty();
        }
    }


    public void deleteAppUser(String id) {
        appUserRepository.deleteById(id);
    }


    public boolean changeStatus(String id, String status) {
        return false;
    }


}
