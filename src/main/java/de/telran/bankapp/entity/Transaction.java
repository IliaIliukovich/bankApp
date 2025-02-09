package de.telran.bankapp.entity;

import de.telran.bankapp.entity.enums.TransactionStatus;
import de.telran.bankapp.entity.enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private TransactionType type;
    private BigDecimal amount;
    private String description;
    private TransactionStatus status;
    private Long debitAccountId;// получатель
    private Long creditAccountId;// отправитель
}
