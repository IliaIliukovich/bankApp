package de.telran.bankapp.service;

import de.telran.bankapp.dto.AppUserDto;
import de.telran.bankapp.entity.AppUser;
import de.telran.bankapp.entity.enums.UserRole;
import de.telran.bankapp.exception.ResourceNotFoundException;
import de.telran.bankapp.mapper.AppUserMapper;
import org.springframework.transaction.annotation.Transactional;
import de.telran.bankapp.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppUserService {
    private final AppUserRepository repository;
    private final AppUserMapper mapper = AppUserMapper.INSTANCE;

    public AppUserService(AppUserRepository repository) {
        this.repository = repository;
    }

    public List<AppUserDto> getAllUsers() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public AppUserDto getUserById(String id) {
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found")));
    }

    @Transactional
    public AppUserDto createUser(AppUserDto userDto) {
        AppUser user = repository.save(mapper.toEntity(userDto));
        return mapper.toDto(user);
    }

    @Transactional
    public void deleteUser(String id) {
        repository.deleteById(id);
    }

    @Transactional
    public AppUserDto updateUser(String id, AppUserDto userDetails) {
        AppUser user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());
        return mapper.toDto(repository.save(user));
    }

    @Transactional
    public AppUserDto patchUser(String id, Map<String, Object> updates) {
        AppUser user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        updates.forEach((key, value) -> {
            switch (key) {
                case "email":
                    user.setEmail((String) value);
                    break;
                case "role":
                    user.setRole(UserRole.valueOf((String) value));
                    break;
            }
        });
        return mapper.toDto(repository.save(user));
    }
}

