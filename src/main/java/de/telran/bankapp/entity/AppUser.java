package de.telran.bankapp.entity;

import de.telran.bankapp.entity.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
  private UserRole role;

}
