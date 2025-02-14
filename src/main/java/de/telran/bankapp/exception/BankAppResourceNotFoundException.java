package de.telran.bankapp.exception;

public class BankAppResourceNotFoundException extends RuntimeException {

    public BankAppResourceNotFoundException(String message) {
        super(message);
    }
}
