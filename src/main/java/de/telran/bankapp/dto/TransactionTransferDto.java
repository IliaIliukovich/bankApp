package de.telran.bankapp.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionTransferDto {

    @NotNull(message = "{validation.transaction.debitAccountId}")
    @Positive(message = "{validation.transaction.debitAccountId}")
    private Long toId;

    @NotNull(message = "{validation.transaction.creditAccountId}")
    @Positive(message = "{validation.transaction.creditAccountId}")
    private Long fromId;

    @NotNull(message = "{validation.transaction.amount}")
    @DecimalMin(value = "0.01", message = "{validation.transaction.amount}")
    private String moneyAmount;

    @NotNull
    @Length(max = 150, message = "{validation.transaction.description}")
    private String description;

    @NotNull(message = "{validation.transaction.type}")
    private String transactionType;

}
