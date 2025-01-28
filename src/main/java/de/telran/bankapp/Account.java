package de.telran.bankapp;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class Account {
    private Long id;
    private String name;
    private AccountType type;
    private AccountStatus status;
    private BigDecimal balance;
    private CurrencyCode currencyCode;
}
