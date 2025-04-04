package de.telran.bankapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Setter
@Getter
public class ExchangeRatesResponse {

    @JsonProperty("data")
    private Map<String, BigDecimal> exchangeRates;

}
