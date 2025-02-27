package de.telran.bankapp.mapper;

import de.telran.bankapp.dto.AppUserDto;
import de.telran.bankapp.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;

@Mapper
public interface AppUserMapper {
    AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);

    @Mapping(target = "password", ignore = true)
    AppUserDto toDto(AppUser user);

    AppUser toEntity(AppUserDto userDto);
}
