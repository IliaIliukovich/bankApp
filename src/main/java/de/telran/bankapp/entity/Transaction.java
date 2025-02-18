package de.telran.bankapp.entity;

import de.telran.bankapp.entity.enums.TransactionStatus;
import de.telran.bankapp.entity.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{validation.transaction.type}")
    private TransactionType type;

    @NotNull(message = "{validation.transaction.amount}")
    @DecimalMin(value = "0.01", message = "{validation.transaction.amount}")
    private BigDecimal amount;

    @Length(max = 150, message = "{validation.transaction.description}")
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{validation.transaction.status}")
    private TransactionStatus status;

    @NotNull(message = "{validation.transaction.debitAccountId}")
    @Positive(message = "{validation.transaction.debitAccountId}")
    private Long debitAccountId;// получатель

    @NotNull(message = "{validation.transaction.creditAccountId}")
    @Positive(message = "{validation.transaction.creditAccountId}")
    private Long creditAccountId;// отправитель

    public Transaction(Long fromId, Long toId, BigDecimal amount, TransactionType transactionType) {
    }
}
