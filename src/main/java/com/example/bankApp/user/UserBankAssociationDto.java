package com.example.bankApp.user;

import com.example.bankApp.embedded.Address;

public record UserBankAssociationDto(
        String firstName,
        String lastName,
        String bankName,
        Address bankAddress
) {
}
