package de.telran.bankapp.dto;

import de.telran.bankapp.entity.enums.AccountStatus;
import de.telran.bankapp.entity.enums.AccountType;
import de.telran.bankapp.entity.enums.CurrencyCode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountPostCreateDto {

    @NotNull(message = "{validation.account.name}")
    @Pattern(regexp = "[A-Z]{2}[0-9]{20}", message = "{validation.account.name}")
    private String name;

    private AccountType type;

    private AccountStatus status;

    private BigDecimal balance;

    private CurrencyCode currencyCode;

    private String clientId;


}
