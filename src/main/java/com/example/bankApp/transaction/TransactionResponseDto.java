package com.example.bankApp.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDto(
        BigDecimal amount,
        LocalDateTime creationDate,
        String paymentDescription
) {
}
