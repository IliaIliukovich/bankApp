package de.telran.bankapp.entity;

// Создать недостающие Rest контроллеры со всеми видами Rest запросов - GET, POST, PATCH, PUT, DELETE
//Разнести всю функциональность по слоям Repository - Service - Controller

import de.telran.bankapp.entity.enums.AccountStatus;
import de.telran.bankapp.entity.enums.AccountType;
import de.telran.bankapp.entity.enums.CurrencyCode;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class Account {


    private Long id;
    private String name;
    private AccountType type;
    private AccountStatus status;
    private BigDecimal balance;
    private CurrencyCode currencyCode;

}
