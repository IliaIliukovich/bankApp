package de.telran.bankapp.dto;

import de.telran.bankapp.entity.enums.AccountType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class AccountCreateDto {

    @Pattern(regexp = "^[a-f0-9\\-]{36}$", message = "{validation.account.clientId}")
    private String clientId;

    @NotNull(message = "{validation.account.productId}")
    @Positive(message = "{validation.account.productId}")
    private Long productId;

    @NotNull(message = "{validation.account.initialAmount}")
    private BigDecimal initialAmount;


    @NotNull(message = "{validation.account.type}")
    private AccountType accountType;

}
