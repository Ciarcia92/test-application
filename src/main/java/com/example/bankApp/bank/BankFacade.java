package com.example.bankApp.bank;

import com.example.bankApp.creditCard.CardService;
import com.example.bankApp.exceptions.ResourceNotFoundException;
import com.example.bankApp.user.User;
import com.example.bankApp.user.UserRepository;
import com.example.bankApp.user.UserMapper;
import com.example.bankApp.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BankFacade {
    private final UserRepository userRepository;
    private final BankMapper bankMapper;
    private final BankRepository bankRepository;

    public BankFacade(UserService userService, UserRepository userRepository, UserMapper userMapper, BankService bankService, BankMapper bankMapper, BankRepository bankRepository, CardService cardService) {
        this.userRepository = userRepository;
        this.bankMapper = bankMapper;
        this.bankRepository = bankRepository;
    }

    public List<BankResponseDto> getBanksByUserId(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> {
                            String errorMsg = String.format("User with id %d not found", id);
                            log.warn(errorMsg);
                            return new ResourceNotFoundException(errorMsg);
                        });
        return user.getBanks().stream()
                .map(bankMapper::toBankResponseDto)
                .collect(Collectors.toList());
    }

    public List<BankResponseDto> getBanksByUserFiscalCode(String fiscalCode) {
        List<BankResponseDto> bankResponseDtos = bankRepository.findBankByUserFiscalCode(fiscalCode)
                .stream().map(bankMapper::toBankResponseDto).collect(Collectors.toList());
        if (bankResponseDtos.isEmpty()) {
            String errorMsg = String.format("No banks found associated with the fiscal code %s", fiscalCode);
            log.warn(errorMsg);
            throw new ResourceNotFoundException(errorMsg);
        }
        return bankResponseDtos;
    }
}
