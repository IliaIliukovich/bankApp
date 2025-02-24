package de.telran.bankapp.entity;

import de.telran.bankapp.entity.enums.TransactionStatus;
import de.telran.bankapp.entity.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private BigDecimal amount;

    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @NotNull(message = "{validation.transaction.debitAccountId}")
    @Positive(message = "{validation.transaction.debitAccountId}")
    @Column(columnDefinition = "int")
    private Long debitAccountId;// получатель

    @NotNull(message = "{validation.transaction.creditAccountId}")
    @Positive(message = "{validation.transaction.creditAccountId}")
    @Column(columnDefinition = "int")
    private Long creditAccountId;// отправитель

}
