package de.telran.bankapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ManagerCreateDto {

    @NotNull(message="{validation.manager.lastName}")
    @Pattern(regexp = "^[A-ZÜÄÖ][a-zA-Züäö]{0,44}$",message = "{validation.manager.lastName}")
    @Length(max = 45,
            message ="{validation.manager.lastName}")
    private String lastName;

    @NotNull(message = "{validation.manager.firstName}")
    @Pattern(regexp = "^[A-ZÜÄÖ][a-zA-Züäö]{0,44}$",message = "{validation.manager.firstName}")
    private String firstName;

    private List<ClientDto> clientsDto;

}
