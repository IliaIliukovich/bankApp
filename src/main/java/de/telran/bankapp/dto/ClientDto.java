package de.telran.bankapp.dto;

import de.telran.bankapp.entity.enums.ClientStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ClientDto {

    private String id;

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

    private ClientStatus status;

    private Long managerId;

    private Integer age;

//    private List<AccountDto> accounts;

}
