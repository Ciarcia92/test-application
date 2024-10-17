package com.example.bankApp.transaction;

import java.time.LocalDateTime;

public record TransactionResponseDto(
        int amount,
        LocalDateTime creationDate,
        String paymentDescription
) {
}
