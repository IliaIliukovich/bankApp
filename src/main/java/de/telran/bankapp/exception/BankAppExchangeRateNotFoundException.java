package de.telran.bankapp.exception;

public class BankAppExchangeRateNotFoundException extends RuntimeException {
    public BankAppExchangeRateNotFoundException(String message) {
        super(message);
    }
}
