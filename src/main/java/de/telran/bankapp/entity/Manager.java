package de.telran.bankapp.entity;

import de.telran.bankapp.entity.enums.ManagerStatus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Manager {

  private Long id;
  private String lastName;
  private String firstName;
  private ManagerStatus status;

}
