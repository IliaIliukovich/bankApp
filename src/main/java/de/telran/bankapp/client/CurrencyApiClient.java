package de.telran.bankapp.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CurrencyApiClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_0TOlHPnDM8YOnYO77EuPTIXpzm53EaMm6CCx4gY9";

    public String getExchangeRates() {
        return restTemplate.getForObject(API_URL, String.class);
    }
}
