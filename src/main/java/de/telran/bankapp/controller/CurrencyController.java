package de.telran.bankapp.controller;

import de.telran.bankapp.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController("/currency")
public class CurrencyController {

    private final CurrencyService service;

    @Autowired
    public CurrencyController(CurrencyService service) {
        this.service = service;
    }


    @GetMapping
    public Map<String, BigDecimal> currentCurrencies() {
            return service.getRates();
    }



}
