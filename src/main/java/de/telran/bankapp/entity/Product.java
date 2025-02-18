package de.telran.bankapp.entity;

import de.telran.bankapp.entity.enums.CurrencyCode;
import de.telran.bankapp.entity.enums.ProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotNull(message = "{validation.product.productNameNotNull}")
    @Size(min = 2, max = 50, message = "{validation.product.productName}")
    private String name;

    @Column(name = "currency_code")
    @NotNull(message = "{validation.product.currencyCodeNotNull}")
    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyCode;

    @Column(name = "interest_rate")
    @NotNull(message = "{validation.product.interestRateNotNull}")
    private Double interestRate;

    @Column(name = "limit_amount")
    @NotNull(message = "Limit amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "{validation.product.amountLessNull")
    private BigDecimal limitAmount;

    @Column(name = "status")
    @NotNull(message = "{validation.product.status}")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

}
