package de.telran.bankapp.entity;

import de.telran.bankapp.entity.enums.ManagerStatus;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Manager {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String lastName;

  private String firstName;

  @Enumerated(EnumType.STRING)
  private ManagerStatus status;

}
