package com.example.bankApp.user;

import com.example.bankApp.embedded.Address;

import java.time.LocalDate;
import java.time.LocalDateTime;


public record UserResponseDto(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String email,
        String fiscalCode,
        Address address,
        LocalDateTime created
) {
}
