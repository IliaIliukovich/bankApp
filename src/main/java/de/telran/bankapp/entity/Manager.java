package de.telran.bankapp.entity;

import de.telran.bankapp.entity.enums.ManagerStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Manager {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "int")
  private Long id;

  private String lastName;

  private String firstName;

  @Enumerated(EnumType.STRING)
  private ManagerStatus status;

  @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
  private List<Client> clients;

}
