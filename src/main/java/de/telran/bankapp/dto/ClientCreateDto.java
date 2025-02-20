package de.telran.bankapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientCreateDto {

    @NotNull(message = "{validation.client.lastName}")
    @Pattern(regexp = "^[A-ZÜÄÖ][a-zA-Züäö]{0,44}$", message = "{validation.client.lastName}")
    private String lastName;

    @NotNull(message = "{validation.client.firstName}")
    @Pattern(regexp = "^[A-ZÜÄÖ][a-zA-Züäö]{0,44}$", message = "{validation.client.firstName}")
    private String firstName;

    private String taxCode;

    @Email(regexp = "^[a-zA-Z][\\w.-]*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message ="{validation.client.email}")
    private String email;

    @Length(max = 150, message ="{validation.client.address}")
    private String address;

    private String phone;

}
