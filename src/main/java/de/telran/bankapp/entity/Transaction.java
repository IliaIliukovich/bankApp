package de.telran.bankapp.entity;

import de.telran.bankapp.entity.enums.TransactionStatus;
import de.telran.bankapp.entity.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

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

//    private Long debitAccountId;// получатель
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "debit_account_id", columnDefinition = "int")
    private Account debitAccount;

//    private Long creditAccountId;// отправитель
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_account_id", columnDefinition = "int")
    private Account creditAccount;
}
