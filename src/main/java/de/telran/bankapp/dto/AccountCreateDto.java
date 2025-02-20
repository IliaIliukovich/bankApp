package de.telran.bankapp.dto;

import de.telran.bankapp.entity.enums.AccountType;
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

    private String clientId;
    private Long productId;
    private BigDecimal initialAmount;
    private AccountType accountType;

}
