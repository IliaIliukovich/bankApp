package de.telran.bankapp.entity;

import de.telran.bankapp.entity.enums.ClientStatus;
import jakarta.persistence.*;
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
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "varchar(36)")
    private String id;

    @NotNull(message = "{validation.client.lastName}")
    @Pattern(regexp = "[a-zA-Z]{1,150}", message = "{validation.client.lastName}")
    private String lastName;

    @NotNull(message = "{validation.client.firstName}")
    @Pattern(regexp = "[a-zA-Z]{1,150}", message = "{validation.client.firstName}")
    private String firstName;

    private String taxCode;

    @Email(regexp = "[a-z\\.]+@[a-z]+\\.[a-z]+", message ="{validation.client.email}")
    private String email;

    @Length(max = 150, message ="{validation.client.address}")
    private String address;

    private String phone;

    @Enumerated(EnumType.STRING)
    private ClientStatus status;

}
