package com.example.bankApp.transaction;

import com.example.bankApp.BankAppApplication;
import com.example.bankApp.transaction.TransactionResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionCardController {

    private final TransactionService transactionService;


    @GetMapping(path = "/card/{card-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionResponseDto> getTransactionByCardId(@PathVariable("card-id") Integer id) {
        log.info("getTransactionByCardId - start");
        List<TransactionResponseDto> transactionList = transactionService.getTransactionByCardId(id);
        log.info("getTransactionByCardId - success, found transaction for card with id {}", id);
        return transactionList;
    }
    @GetMapping(path = "/user/{user-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionResponseDto> getTransactionByUserId(@PathVariable("user-id") Integer id) {
        log.info("getTransactionByUserId - start");
        List<TransactionResponseDto> transactionList = transactionService.getTransactionByUserId(id);
        log.info("getTransactionByUserId - success, found transaction for user with id {}", id);
        return transactionList;
    }

//    @GetMapping(path = "/date/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<TransactionResponseDto> getTransactionByDate(@PathVariable("date") Integer id) {
//        log.info("getTransactionByUserId - start");
//        List<TransactionResponseDto> transactionList = transactionService.getTransactionByDate(id);
//        log.info("getTransactionByUserId - success, found transaction for user with id {}", id);
//        return transactionList;
//    }



}
