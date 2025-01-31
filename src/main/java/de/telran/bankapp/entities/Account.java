package de.telran.bankapp.entities;

import de.telran.bankapp.entities.enums.AccountStatus;
import de.telran.bankapp.entities.enums.AccountType;
import de.telran.bankapp.entities.enums.CurrencyCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Account {

    private Long id;
    private String name;
    private AccountType type;
    private AccountStatus status;
    private BigDecimal balance;
    private CurrencyCode currencyCode;
}
