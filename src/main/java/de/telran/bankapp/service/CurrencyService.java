package de.telran.bankapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.bankapp.client.CurrencyApiClient;
import de.telran.bankapp.dto.ExchangeRatesResponse;
import de.telran.bankapp.entity.enums.CurrencyCode;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class CurrencyService {

    private final CurrencyApiClient currencyApiClient;
    private final ObjectMapper objectMapper;

    public CurrencyService(CurrencyApiClient currencyApiClient, ObjectMapper objectMapper) {
        this.currencyApiClient = currencyApiClient;
        this.objectMapper = objectMapper;
    }

    public Map<String, BigDecimal> getRates() {
        try {
            String json = currencyApiClient.getExchangeRates();
            ExchangeRatesResponse response = objectMapper.readValue(json, ExchangeRatesResponse.class);
            return response.getExchangeRates();
        } catch (IOException e) {
            throw new BankAppResourceNotFoundException("Cannot get current currency rates from third party API");
        }
    }

    public BigDecimal convertAmountToRequiredCurrency(BigDecimal amount,
                                                      CurrencyCode currencyFrom,
                                                      CurrencyCode currencyTo,
                                                      Map<String, BigDecimal> currencyRates) {
        if (currencyFrom.equals(currencyTo)) return amount;
        BigDecimal bankFee = new BigDecimal("0.98");
        BigDecimal resultCurrencyAmount = currencyRates.get(currencyFrom.name());
        if (currencyFrom != CurrencyCode.USD) {
            resultCurrencyAmount = amount.divide(resultCurrencyAmount, 10, RoundingMode.HALF_UP);
        }
        if (currencyTo != CurrencyCode.USD) {
            resultCurrencyAmount = amount.multiply(currencyRates.get(currencyTo.name()));
        }
        resultCurrencyAmount = resultCurrencyAmount.multiply(bankFee);
        return resultCurrencyAmount.setScale(2, RoundingMode.DOWN);
    }
}
