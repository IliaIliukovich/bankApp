package de.telran.bankapp.entities;

import de.telran.bankapp.entities.enums.CurrencyCode;
import de.telran.bankapp.entities.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Product {
    private Long id;
    private String name;
    private CurrencyCode currencyCode;
    private Double interestRate;
    private BigDecimal limitAmount;
    private ProductStatus status;
}
