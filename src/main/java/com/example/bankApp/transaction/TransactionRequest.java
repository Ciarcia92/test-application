package com.example.bankApp.transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionRequest {

    @NotNull(message = "Credit card id is required")
    private Integer cardId;

    @NotBlank(message = "PIN REQUIRED")
    private String pin;

    @NotNull(message = "Amount required")
    private BigDecimal amount;

    private String paymentDescription;
}
