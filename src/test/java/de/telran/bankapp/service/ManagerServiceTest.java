package de.telran.bankapp.service;

import de.telran.bankapp.dto.ManagerDto;
import de.telran.bankapp.entity.Manager;
import de.telran.bankapp.entity.enums.ManagerStatus;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
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

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void getManagerById(){
        Long managerId = 20L;
        Optional<Manager> manager = Optional.of(new Manager());
        ManagerDto managerDto = new ManagerDto();

        when(repository.findById(managerId)).thenReturn(manager);
        when(mapper.entityToDto(manager.get())).thenReturn(managerDto);

        service.getManagerById(managerId);

        verify(mapper).entityToDto(manager.get());
    }

    @Test
    void getManagerByIdNotFound(){ // TODO
    }

    @Test
    void changeManagerStatus(){
        Long id = 2L;
        ManagerStatus status = ManagerStatus.INACTIVE;
        int updated = 1;

        when(repository.updateStatus(id, status)).thenReturn(updated);
        service.changeManagerStatus(id, status);
        verify(repository).updateStatus(id, status);
    }

    @Test
    void changeManagerStatusNull(){
        Long id = 2L;
        ManagerStatus status = null;
        int updated = 1;

        when(repository.updateStatus(id, ManagerStatus.ACTIVE)).thenReturn(updated);
        service.changeManagerStatus(id, status);
        verify(repository).updateStatus(id, ManagerStatus.ACTIVE);
    }

    @Test
    void changeManagerStatusException(){
        Long id = 20L;
        int updated = 0;

        when(repository.updateStatus(id, ManagerStatus.ACTIVE)).thenReturn(updated);
        assertThrows(BankAppResourceNotFoundException.class, ()->service.changeManagerStatus(id, ManagerStatus.ACTIVE));
    }

}