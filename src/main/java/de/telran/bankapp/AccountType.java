package de.telran.bankapp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountType {
    CHECKING,
    SAVINGS,
    LOAN,
    DEBIT_CARD,
    CREDIT_CARD,
    OTHER
}
