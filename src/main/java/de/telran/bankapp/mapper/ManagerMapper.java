package de.telran.bankapp.mapper;

import de.telran.bankapp.dto.ManagerCreateDto;
import de.telran.bankapp.dto.ManagerDto;
import de.telran.bankapp.entity.Manager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


import java.util.List;

@Mapper(componentModel = "spring", uses = ClientMapper.class)
public interface ManagerMapper {

    ManagerMapper INSTANCE = Mappers.getMapper(ManagerMapper.class);

    Manager dtoToEntity(ManagerDto dto);

    @Mapping(target = "clientsDto", source = "clients")
    ManagerDto entityToDto(Manager entity);

    List<ManagerDto> entityListToDto(List<Manager> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    Manager createDtoToEntity(ManagerCreateDto dto);

}
