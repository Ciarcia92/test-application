package com.example.bankApp.transaction;

import com.example.bankApp.creditCard.CardRepository;
import com.example.bankApp.creditCard.CreditCard;
import com.example.bankApp.exceptions.InvalidPinException;
import com.example.bankApp.exceptions.ResourceNotFoundException;
import com.example.bankApp.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    public List<Transaction> getAllTransaction() {
        List<Transaction> transactions = transactionRepository.findAll();
        if (transactions.isEmpty()) {
            log.warn("No transactions found");
            throw new ResourceNotFoundException("No transactions found");
        }
        return transactions;
    }

    public List<TransactionResponseDto> getAllTransactionDto() {
        List<TransactionResponseDto> transactionResponseDtos = transactionRepository.findAll()
                .stream()
                .map(transactionMapper::toTransactionResponseDto)
                .collect(Collectors.toList());
        if (transactionResponseDtos.isEmpty()) {
            log.warn("No transactions found");
            throw new ResourceNotFoundException("No transactions found");
        }
        return transactionResponseDtos;
    }

    public List<TransactionResponseDto> getTransactionByCardId(Integer id) {
        List<TransactionResponseDto> transactionResponseDtos = transactionRepository.findTransactionByCardId(id)
                .stream().map(transactionMapper::toTransactionResponseDto)
                .collect(Collectors.toList());
        if (transactionResponseDtos.isEmpty()) {
            log.warn("No transactions found");
            throw new ResourceNotFoundException("No transactions found");
        }
        return transactionResponseDtos;
    }
    //    public List<TransactionResponseDto> getTransactionByCardId(Integer id) {
    //        cardRepository.findById(id).orElseThrow(() -> {
    //            String errorMsg = String.format("cARD with id %d not found", id);
    //            log.warn(errorMsg);
    //            return new ResourceNotFoundException(errorMsg);
    //        });
    //        List<TransactionResponseDto> transactionResponseDtos = transactionRepository.findTransactionByCardId().stream()
    //                .map(transactionMapper::toTransactionResponseDto)
    //                .collect(Collectors.toList());
    //        if (transactionResponseDtos.isEmpty()) {
    //            log.warn("No transaction found for card with id {}", id);
    //        } return transactionResponseDtos;
    //    }

    public List<TransactionResponseDto> getTransactionByUserId(Integer id) {
        userRepository.findById(id).orElseThrow(() -> {
            String errorMsg = String.format("User with id %d not found", id);
            log.warn(errorMsg);
            return new ResourceNotFoundException(errorMsg);
        });
        List<TransactionResponseDto> transactionResponseDtos = transactionRepository.findTransactionByUserId(id).stream()
                .map(transactionMapper::toTransactionResponseDto)
                .collect(Collectors.toList());
        if (transactionResponseDtos.isEmpty()) {
            log.warn("No transaction found for user with id {}", id);
        }
        return transactionResponseDtos;
    }


    public TransactionResponseDto makeTransaction(TransactionRequest transactionRequest) {
        CreditCard creditCard = cardRepository.findById(transactionRequest.getCardId()).orElseThrow(() -> {
            String errorMsg = String.format("Credit card with id %d not found", transactionRequest.getCardId());
            log.warn(errorMsg);
            return new ResourceNotFoundException(errorMsg);
        });

        if (!creditCard.getPin().equals(transactionRequest.getPin())){
            throw new InvalidPinException("Invalid PIN");
        }
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setPaymentDescription(transactionRequest.getPaymentDescription());
        transaction.setCreditCard(creditCard);

        transactionRepository.save(transaction);

        return transactionMapper.toTransactionResponseDto(transaction);
    }
}