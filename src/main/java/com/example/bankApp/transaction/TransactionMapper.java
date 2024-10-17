package com.example.bankApp.transaction;

import org.springframework.stereotype.Service;

@Service
public class TransactionMapper {

    public TransactionResponseDto toTransactionResponseDto(Transaction transaction) {
        return new TransactionResponseDto(
                transaction.getAmount(),
                transaction.getCreationDate(),
                transaction.getPaymentDescription()
        );
    }
}
