package de.telran.bankapp.entity;

import de.telran.bankapp.entity.enums.AgreementStatus;
import jakarta.persistence.*;
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
  @Column(columnDefinition = "DECIMAL(15,2)")
  private Double interestRate; // процентная ставка
  @Enumerated(EnumType.STRING)
  private AgreementStatus status;
  private BigDecimal sum;
  @Column(columnDefinition = "int")
  private Long accountId;
  @Column(columnDefinition = "int")
  private Long productId;

}
