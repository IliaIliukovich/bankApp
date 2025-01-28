package de.telran.bankapp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountStatus {
    ACTIVE,
    INACTIVE,
    BLOCKED
}
