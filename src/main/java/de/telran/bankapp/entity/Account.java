package de.telran.bankapp.entity;


import de.telran.bankapp.entity.enums.AccountStatus;
import de.telran.bankapp.entity.enums.AccountType;
import de.telran.bankapp.entity.enums.CurrencyCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity

public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{validation.client.name}")
    @Pattern(regexp = "[a-zA-Z]{1,150}", message = "{validation.client.name}")
    private String name;

    @Enumerated(EnumType.STRING)
    private AccountType Type;

    @Enumerated(EnumType.STRING)
    private AccountStatus Status;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyCode;

}
