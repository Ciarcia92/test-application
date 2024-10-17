package com.example.bankApp.transaction;

import java.time.LocalDateTime;

public record TransactionDto(
        int amount,
        LocalDateTime creationDate,
        String paymentDescription
) {
}
