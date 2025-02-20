package de.telran.bankapp.mapper;

import de.telran.bankapp.dto.ClientCreateDto;
import de.telran.bankapp.dto.ClientDto;
import de.telran.bankapp.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client dtoToEntity(ClientDto dto);

    ClientDto entityToDto(Client entity);

    List<ClientDto> entityListToDto(List<Client> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    Client createDtoToEntity(ClientCreateDto dto);
}
