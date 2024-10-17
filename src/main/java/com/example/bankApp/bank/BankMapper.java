package com.example.bankApp.bank;

import org.springframework.stereotype.Service;

@Service
public class BankMapper {

    public BankResponseDto toBankResponseDto(Bank bank) {
        return new BankResponseDto(
                bank.getName(),
                bank.getAddress()
        );
    }
}
