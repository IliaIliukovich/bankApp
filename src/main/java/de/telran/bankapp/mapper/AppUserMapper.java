package de.telran.bankapp.mapper;

import de.telran.bankapp.dto.AppUserDto;
import de.telran.bankapp.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    @Mapping(target = "password", constant = "***")
    AppUserDto toDto(AppUser user);

    AppUser toEntity(AppUserDto userDto);
}
