package com.example.bankApp.bank;

import com.example.bankApp.exceptions.DuplicateResourceException;
import com.example.bankApp.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class BankService {
    public final BankMapper bankMapper;

    public final BankRepository bankRepository;

    public BankService(BankMapper bankMapper, BankRepository bankRepository) {
        this.bankMapper = bankMapper;
        this.bankRepository = bankRepository;
    }

    public List<Bank> getAllBank() {
        List<Bank> banks = bankRepository.findAll();
        if (banks.isEmpty()) {
            log.warn("No banks found");
            throw new ResourceNotFoundException("No Banks found");
        }
        return banks;
    }

    public List<BankResponseDto> getAllBankDto() {
        List<BankResponseDto> bankResponseDtos = bankRepository.findAll()
                .stream()
                .map(bankMapper::toBankResponseDto)
                .collect(Collectors.toList());
        if (bankResponseDtos.isEmpty()) {
            log.warn("No banks found");
            throw new ResourceNotFoundException("No Banks found");
        }
        return bankResponseDtos;
    }

    public Bank getBank(Integer id) {
        return bankRepository.findById(id).orElseThrow(
                () -> {
                    String errorMsg = String.format("Bank with id %d not found", id);
                    log.warn(errorMsg);
                    return new ResourceNotFoundException(errorMsg);
                }
        );
    }

    public BankResponseDto getBankDtoById(Integer id) {
        return bankRepository.findById(id).map(bank -> bankMapper.toBankResponseDto(bank))
                .orElseThrow(
                        () -> {
                            String errorMsg = String.format("Bank with id %d not found", id);
                            log.warn(errorMsg);
                            return new ResourceNotFoundException(errorMsg);
                        }
                );
    }

    public Bank addBank(Bank newBank) {
        Optional<Bank> bankToSave = bankRepository.findById(newBank.getId());
        if (bankToSave.isPresent()) {
            String errorMsg = String.format("Bank with id %s already present", newBank.getId());
            log.warn(errorMsg);
            throw new DuplicateResourceException(errorMsg);
        }
        return bankRepository.save(newBank);
    }

    public Bank getBankByBankNumberIdentifier(String bankNumberIdentifier) {
        return bankRepository.findBankByBankNumberIdentifier(bankNumberIdentifier)
                .orElseThrow(() -> {
                    String errorMsg = String.format("Bank with identifier %s not found", bankNumberIdentifier);
                    log.warn(errorMsg);
                    return new ResourceNotFoundException(errorMsg);
                });
    }
    public BankResponseDto getBankDtoByNumberIdentifier(String bankNumberIdentifier) {
        return  bankRepository.findBankByBankNumberIdentifier(bankNumberIdentifier).map(bankMapper::toBankResponseDto)
                .orElseThrow( () -> {
                    String errorMsg = String.format("Bank with identifier %s not found", bankNumberIdentifier);
                    log.warn(errorMsg);
                    return new ResourceNotFoundException(errorMsg);
                });
    }

    public void deleteBankById(Integer id) {
        Bank bank = bankRepository.findById(id)
                .orElseThrow(
                        () -> {
                            String errorMsg = String.format("Bank with ID %d not found", id);
                            log.warn(errorMsg);
                            return new ResourceNotFoundException(errorMsg);
                        }
                );
        bankRepository.deleteById(id);
        log.info("deleteBankById - deleted bank with id {}", id);

    }

    public void deleteByIdentificationNumber(String identificationNumber) {
        Bank bank = bankRepository.findBankByBankNumberIdentifier(identificationNumber)
                .orElseThrow(
                        () -> {
                            String errorMsg = String.format("Bank with identification number %d not found", identificationNumber);
                            log.warn(errorMsg);
                            return new ResourceNotFoundException(errorMsg);
                        }
                );
        bankRepository.deleteByBankNumberIdentifier(identificationNumber);
        log.info("deleteByIdentificationNumber - deleted bank with identification number {}", identificationNumber);
    }

    public String generateBankIdentificationNumber() {
        Random random = new Random();
        StringBuilder bankIdentificationNumber = new StringBuilder();
        bankIdentificationNumber.append((char) ('A' + random.nextInt(26)));
        for (int i = 0; i < 9; i++) {
            bankIdentificationNumber.append(random.nextInt(10));
        } return bankIdentificationNumber.toString();
    }

}
