package com.example.bankApp.exceptions;

public class InvalidPinException extends RuntimeException{
    public InvalidPinException(String message) {
        super(message);
    }
}
