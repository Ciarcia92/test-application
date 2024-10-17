package com.example.bankApp.creditCard;

import com.example.bankApp.bank.Bank;
import com.example.bankApp.user.User;

import java.math.BigInteger;
import java.time.LocalDateTime;

public record CardDto(
        BigInteger cardNumber,
        LocalDateTime dateOfRelease,
        User user,
        Bank bank
) {
}
