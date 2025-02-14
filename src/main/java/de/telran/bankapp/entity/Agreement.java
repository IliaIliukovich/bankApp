package de.telran.bankapp.entity;

import de.telran.bankapp.entity.enums.AgreementStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Agreement {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private Double interestRate; // процентная ставка
  private AgreementStatus status;
  private BigDecimal sum;
  private Long accountId;
  private Long productId;

}
