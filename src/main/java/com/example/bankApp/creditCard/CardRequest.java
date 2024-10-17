package com.example.bankApp.creditCard;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CardRequest(

        Integer id,
        @NotNull(message = "100")
        @NotEmpty(message = "100")
        String iban,
        @NotNull(message = "101")
        @NotEmpty(message = "101")
        Long cardNumber,
        @NotNull(message = "102")
        @NotEmpty(message = "102")
        LocalDateTime dateOfRelease
) {
}
