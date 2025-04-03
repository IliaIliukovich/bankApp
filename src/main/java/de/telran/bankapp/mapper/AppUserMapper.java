package de.telran.bankapp.mapper;

import de.telran.bankapp.dto.AppUserDto;
import de.telran.bankapp.dto.AppUserRegisterDto;
import de.telran.bankapp.entity.AppUser;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    @Mapping(target = "password", constant = "***")
    AppUserDto toDto(AppUser user);

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    AppUser toEntity(AppUserDto userDto);

    @Mapping(target = "role", constant = "CLIENT")
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "id", ignore = true)
    AppUser registerDtoToEntity(@Valid AppUserRegisterDto dto);
}
