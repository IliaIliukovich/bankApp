package de.telran.bankapp.entity;

import de.telran.bankapp.entity.enums.CurrencyCode;
import de.telran.bankapp.entity.enums.ProductStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public
class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private CurrencyCode currencyCode;
    private Double interestRate;
    private BigDecimal limitAmount;
    private ProductStatus status;
}
