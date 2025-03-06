package de.telran.bankapp.mapper;

import de.telran.bankapp.dto.ManagerCreateDto;
import de.telran.bankapp.dto.ManagerDto;
import de.telran.bankapp.entity.Client;
import de.telran.bankapp.entity.Manager;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


import java.util.List;

@Mapper(componentModel = "spring", uses = ClientMapper.class)
public abstract class ManagerMapper {

    @Mapping(target = "clients", source = "clientsDto")
    public abstract Manager dtoToEntity(ManagerDto dto);

    @Mapping(target = "clientsDto", source = "clients")
    public abstract ManagerDto entityToDto(Manager entity);

    public abstract List<ManagerDto> entityListToDto(List<Manager> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "clients", source = "clientsDto")
    public abstract Manager createDtoToEntity(ManagerCreateDto dto);

    @AfterMapping
    public void setManagerForClient(@MappingTarget Manager manager){
        for (Client c : manager.getClients()) {
            c.setManager(manager);
        }
    }

}
