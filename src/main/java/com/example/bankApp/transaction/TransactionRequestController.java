package com.example.bankApp.transaction;

import com.example.bankApp.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionRequestController {

    private final TransactionService transactionService;

    @PostMapping(path = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponseDto> processTransaction(@Valid @RequestBody TransactionRequest transactionRequest, Authentication authentication) {
        log.info("processing Transaction for credit card with id {}", transactionRequest.getCardId());
        User client = (User) authentication.getPrincipal();
        log.info("Authenticated user {}", client.getFullName());
        TransactionResponseDto transaction = transactionService.makeTransaction(transactionRequest);
        return ResponseEntity.ok(transaction);

    }
//    @PostMapping(path = "/card/{card-id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<TransactionDto> makeTransaction(@RequestBody TransactionDto transaction, @PathVariable("card-id") Integer cardId) {
//        log.info("makeTransaction - start");
//        TransactionDto transactionDto = transactionService.makeTransaction(transaction, cardId);
//        return null;
//    }
}
