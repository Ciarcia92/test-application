package com.example.bankApp.creditCard;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CardRequest(

        Integer id,

        String iban,
        Long cardNumber,
        LocalDateTime dateOfRelease
) {
}
