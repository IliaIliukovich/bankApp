package de.telran.bankapp.dto;

import de.telran.bankapp.entity.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppUserDto {

    private String id;
    @Email(regexp = "^[a-zA-Z][\\w.-]*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "{validation.user.email}")
    private String email;
    @Size(min = 6, message = "{validation.user.password}")
    private String password;
    private UserRole role;
}
