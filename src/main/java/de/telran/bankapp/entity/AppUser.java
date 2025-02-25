package de.telran.bankapp.entity;

import de.telran.bankapp.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class AppUser {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String email;
  private String password;
  @Enumerated(EnumType.STRING)
  private UserRole role;

}
