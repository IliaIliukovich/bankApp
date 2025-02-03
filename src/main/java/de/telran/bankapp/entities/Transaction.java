package de.telran.bankapp.entities;

import de.telran.bankapp.entities.enums.TransactionStatus;
import de.telran.bankapp.entities.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class Transaction {
    private String id;
    private TransactionType type;
    private BigDecimal amount;
    private String description;
    private TransactionStatus status;
    private Long debitAccountId;// получатель
    private Long creditAccountId;// отправитель
}
