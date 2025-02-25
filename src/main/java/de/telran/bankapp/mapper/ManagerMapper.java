package de.telran.bankapp.mapper;

import de.telran.bankapp.dto.ManagerCreateDto;
import de.telran.bankapp.dto.ManagerDto;
import de.telran.bankapp.entity.Manager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(componentModel = "spring")
public interface ManagerMapper {

    Manager dtoToEntity(ManagerDto dto);

    ManagerDto entityToDto(Manager entity);

    List<ManagerDto> entityListToDto(List<Manager> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
     Manager createDtoToEntity(ManagerCreateDto dto);

}
