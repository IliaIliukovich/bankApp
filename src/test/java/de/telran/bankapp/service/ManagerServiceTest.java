package de.telran.bankapp.service;

import de.telran.bankapp.dto.ManagerDto;
import de.telran.bankapp.entity.Manager;
import de.telran.bankapp.mapper.ClientMapper;
import de.telran.bankapp.mapper.ManagerMapper;
import de.telran.bankapp.repository.ClientRepository;
import de.telran.bankapp.repository.ManagerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.*;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManagerServiceTest {
    @Mock
    private ManagerRepository repository;
    @Mock
    private ManagerMapper mapper;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    public ClientMapper clientMapper;

    @InjectMocks
    private ManagerService service;

    @Test
    void findByName() {
        String  firstName = "abv";
        List<Manager> list = new ArrayList<>();
        when(repository.findByFirstName(firstName)).thenReturn(list);
        service.findByName(firstName);
        verify(mapper).entityListToDto(list);
    }
}