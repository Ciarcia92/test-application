package com.example.bankApp.creditCard;

import com.example.bankApp.bank.Bank;
import com.example.bankApp.embedded.Address;
import com.example.bankApp.user.User;

public record CardResponseDto(
        User user,
        String bankName,

        Address bankAddress
) {
}
