package de.telran.bankapp.entity;

import de.telran.bankapp.entity.enums.CurrencyCode;
import de.telran.bankapp.entity.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class Product {
    private Long id;
    private String name;
    private CurrencyCode currencyCode;
    private Double interestRate;
    private BigDecimal limitAmount;
    private ProductStatus status;
}
