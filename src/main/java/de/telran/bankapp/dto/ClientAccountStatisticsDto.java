package de.telran.bankapp.dto;

import de.telran.bankapp.entity.enums.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientAccountStatisticsDto {

    private Map<CurrencyCode, BigDecimal> balanceByCurrency;
    private Map<CurrencyCode, BigDecimal> incomesByCurrency;
    private Map<CurrencyCode, BigDecimal> expensesByCurrency;
}
