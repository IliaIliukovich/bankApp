package de.telran.bankapp.dto;

import de.telran.bankapp.entity.enums.CurrencyCode;
import de.telran.bankapp.entity.enums.ProductStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductCreateDto {

    @NotNull(message = "{validation.product.productNameNotNull}")
    @Size(min = 2, max = 50, message = "{validation.product.productName}")
    private String name;

    @NotNull(message = "{validation.product.currencyCodeNotNull}")
    private CurrencyCode currencyCode;

    @NotNull(message = "{validation.product.interestRateNotNull}")
    private Double interestRate;

    @NotNull(message = "{validation.product.limitAmount}")
    @DecimalMin(value = "0.0", inclusive = false, message = "{validation.product.amountLessNull}")
    private BigDecimal limitAmount;

    private ProductStatus status;
}