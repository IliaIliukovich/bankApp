package de.telran.bankapp.service;

import de.telran.bankapp.entity.Client;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
import de.telran.bankapp.mapper.ClientMapper;
import de.telran.bankapp.repository.ClientRepository;
import de.telran.bankapp.repository.ManagerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository repository;
    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private ClientMapper mapper;  // Mappers.getMapper(ClientMapper.class);

    @InjectMocks
    private ClientService service;

    @Test
    void getAll() {
        service.getAll();
        verify(repository, times(1)).findAll();
        verify(mapper).entityListToDto(any());
    }

    @Test
    void updateAddressIdNotFound() {
        String uuid = "123";
        when(repository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(BankAppResourceNotFoundException.class, () -> service.updateAddress(uuid, "New address"));
        verify(repository).findById(uuid);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAddressIdFound() {
        String uuid = "123";
        Client client = new Client();
        client.setId(uuid);
        when(repository.findById(uuid)).thenReturn(Optional.of(client));

        String newAddress = "New address";
        service.updateAddress(uuid, newAddress);
        assertEquals(newAddress, client.getAddress());
        verify(repository).save(client);
        verify(mapper).entityToDto(any());
    }
}