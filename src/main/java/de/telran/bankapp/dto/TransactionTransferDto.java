package de.telran.bankapp.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

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
    private BigDecimal moneyAmount;

}
