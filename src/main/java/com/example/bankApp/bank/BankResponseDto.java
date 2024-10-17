package com.example.bankApp.bank;

import com.example.bankApp.embedded.Address;

public record BankResponseDto(
        String name,
        Address address
) {
}
