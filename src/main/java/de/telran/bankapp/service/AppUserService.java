package de.telran.bankapp.service;

import de.telran.bankapp.dto.AppUserDto;
import de.telran.bankapp.dto.AppUserRegisterDto;
import de.telran.bankapp.entity.AppUser;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
import de.telran.bankapp.mapper.AppUserMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import de.telran.bankapp.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AppUserService {

    private final AppUserRepository repository;
    private final AppUserMapper mapper;
    private final PasswordEncoder encoder;

    @Autowired
    public AppUserService(AppUserRepository repository, AppUserMapper mapper, PasswordEncoder encoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.encoder = encoder;
    }

    public List<AppUserDto> getAllUsers() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public AppUserDto getUserById(String id) {
        AppUser user = repository.findById(id).orElseThrow(() -> new BankAppResourceNotFoundException("User with id = " + id + " not found"));
        return mapper.toDto(user);
    }

    @Transactional
    public AppUserDto createUser(AppUserDto userDto) {
        AppUser entity = mapper.toEntity(userDto);
        entity.setPassword(encoder.encode(entity.getPassword()));
        AppUser user = repository.save(entity);
        return mapper.toDto(user);
    }

    @Transactional
    public AppUserDto register(@Valid AppUserRegisterDto dto) {
        AppUser entity = mapper.registerDtoToEntity(dto);
        entity.setPassword(encoder.encode(entity.getPassword()));
        AppUser user = repository.save(entity);
        return mapper.toDto(user);
    }

    @Transactional
    public void deleteUser(String id) {
        repository.deleteById(id);
    }

    @Transactional
    public AppUserDto updateUser(AppUserDto userDetails) {
        if (userDetails.getId() != null) {
            Optional<AppUser> optional = repository.findById(userDetails.getId());
            if (optional.isPresent()) {
                AppUser user = optional.get();
                user.setEmail(userDetails.getEmail());
                user.setRole(userDetails.getRole());
                return mapper.toDto(repository.save(user));
            }
        }
        throw new BankAppResourceNotFoundException("User with id = " + userDetails.getId() + " not found");
    }

    public Optional<AppUser> getByLogin(String login) {
        return repository.findAppUserByEmail(login);
    }

    @Transactional
    public void save(AppUser user) {
        repository.save(user);
    }
}

