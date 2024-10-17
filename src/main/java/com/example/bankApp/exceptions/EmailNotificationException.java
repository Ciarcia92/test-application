package com.example.bankApp.exceptions;

import jakarta.mail.MessagingException;

public class EmailNotificationException extends RuntimeException {
    public EmailNotificationException(String message, MessagingException e) {
        super(message, e);
    }
}
