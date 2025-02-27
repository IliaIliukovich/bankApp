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

    @NotNull(message = "{validation.account.type}")
    private AccountType type;

    @NotNull(message = "{validation.account.status}")
    private AccountStatus status;

    @NotNull(message = "{validation.account.balance}")
    private BigDecimal balance;

    @NotNull(message = "{validation.account.currencyCode}")
    private CurrencyCode currencyCode;

    @Pattern(regexp = "^[a-f0-9\\-]{36}$", message = "{validation.account.clientId}")
    private String clientId;

}
