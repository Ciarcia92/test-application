package com.example.bankApp.bank;

import com.example.bankApp.embedded.Address;

public record BankDto(
        String name,
        Address address,
        String bankNumberIdentifier
) {
}
