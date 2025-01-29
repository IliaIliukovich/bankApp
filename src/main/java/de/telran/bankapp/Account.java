package de.telran.bankapp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Account {

    private Long id;
    private String name;
    private AccountType type;
    private AccountStatus status;
    private BigDecimal balance;
    private CurrencyCode currencyCode;

//    public Account() {
//    }

//    public Account(Long id, String name, AccountType type, AccountStatus status, BigDecimal balance, CurrencyCode currencyCode) {
//        this.id = id;
//        this.name = name;
//        this.type = type;
//        this.status = status;
//        this.balance = balance;
//        this.currencyCode = currencyCode;
//    }
}


