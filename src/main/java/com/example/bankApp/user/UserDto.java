package com.example.bankApp.user;

import com.example.bankApp.embedded.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserDto(

        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotNull
        LocalDate dateOfBirth,
        @NotBlank
        String email,
        @NotBlank
        String fiscalCode,
        @NotBlank
        Address address,
        @NotNull
        LocalDateTime created
) {
}
