package de.telran.bankapp.entity;

import de.telran.bankapp.entity.enums.ClientStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String lastName;

    private String firstName;

    private String taxCode;

    private String email;

    private String address;

    private String phone;

    @Enumerated(EnumType.STRING)
    private ClientStatus status;

}
