package de.telran.bankapp.exception;

public class BankAppBadRequestException extends RuntimeException{

    public BankAppBadRequestException(String message) {
        super(message);
    }
}
