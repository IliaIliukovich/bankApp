package de.telran.bankapp.dto;

import de.telran.bankapp.entity.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppUserDto {
    private String id;
    private String email;
    private String password;
    private UserRole role;
}
