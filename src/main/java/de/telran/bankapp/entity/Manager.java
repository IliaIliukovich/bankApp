package de.telran.bankapp.entity;

import de.telran.bankapp.entity.enums.ManagerStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

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
  private Long id;

  @NotNull(message="{validation.manager.lastName}")
  @Pattern(regexp = "^[A-ZÜÄÖ][a-zA-Züäö]{0,44}$",message = "{validation.manager.lastName}")
  @Length(max = 45,
          message ="{validation.manager.lastName}")
  private String lastName;

  @NotNull(message = "{validation.manager.firstName}")
  @Pattern(regexp = "^[A-ZÜÄÖ][a-zA-Züäö]{0,44}$",message = "{validation.manager.firstName}")
  private String firstName;

  @Enumerated(EnumType.STRING)
  private ManagerStatus status;

  @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
  private List<Client> clients;

}
