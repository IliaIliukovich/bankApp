package de.telran.bankapp.entity;

import de.telran.bankapp.entity.enums.UserRole;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String email;
  private String password;
  @Enumerated(EnumType.STRING)
  private UserRole role;

}


