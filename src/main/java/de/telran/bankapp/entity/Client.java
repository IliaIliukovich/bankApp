package de.telran.bankapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.telran.bankapp.entity.enums.ClientStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
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

    // unidirectional -- bidirectional
    // One to One
    // One to Many
    // Many to One
    // Many to Many

//    private Long manager_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Manager manager;

    @OneToMany(mappedBy = "client")
    private List<Account> accounts;

}
