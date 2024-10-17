package com.example.bankApp.user;

import com.example.bankApp.bank.BankFacade;
import com.example.bankApp.user.UserFacade;
import com.example.bankApp.bank.BankResponseDto;
import com.example.bankApp.user.UserResponseDto;
import com.example.bankApp.bank.BankService;
import com.example.bankApp.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class  UserBankController {

    private final UserFacade userFacade;
    private final BankFacade bankFacade;

    public UserBankController(UserService userService, UserFacade userFacade, BankService bankService, BankFacade bankFacade) {
        this.userFacade = userFacade;
        this.bankFacade = bankFacade;
    }

    //    get all the users associated to a bank
    @GetMapping(path = "/bank/{bank-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserResponseDto> getUsersByBankId(@PathVariable("bank-id") Integer id) {
        log.info("getUserByBankId - start");
        List<UserResponseDto> userList = userFacade.getUserByBankId(id);
        log.info("getUserByBankId - success, found {} users for bank with id {}", userList.size(), id);
        return userList;
    }

    // get all the users associated to a specified bank number identifier
    @GetMapping(path = "/bank/number/{identification-number}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserResponseDto> getUsersByBankIdentificationNumber(@PathVariable("identification-number") String identificationNumber) {
        log.info("getUserByBankIdentificationNumber - start");
        List<UserResponseDto> userList = userFacade.getUserByBankIdentificationNumber(identificationNumber);
        log.info("getUserByBankIdentificationNumber - success, found {} users for bank with id {}", userList.size(), identificationNumber);
        return userList;
    }

//    get all the bank associated with a fiscal code
    @GetMapping(path = "/bank/fiscal-code/{fiscal-code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BankResponseDto> getBankByUserFiscalCode(@PathVariable("fiscal-code") String fiscalCode) {
        log.info("getBankByUserFiscalCode - start");
        List<BankResponseDto> bankList = bankFacade.getBanksByUserFiscalCode(fiscalCode);
        log.info("getBankByUserFiscalCode - success, found {} banks associated to the user with fiscal code {}", bankList.size(), fiscalCode);
        return bankList;
    }

    //    get all the banks a user is associated with
    @GetMapping(path = "/{user-id}/bank", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BankResponseDto> getBankByUserId(@PathVariable("user-id") Integer id) {
        log.info("getBankByUserId - start");
        List<BankResponseDto> bankList = bankFacade.getBanksByUserId(id);
        log.info("getBankByUserId - success, found {} banks for user with id {}", bankList.size(), id);
        return bankList;
    }


    //    associate user with bank
//    @PatchMapping(path = "/{user-id}/bank")
//    public User associateUserToBank(@PathVariable("user-id") Integer userId, @RequestBody BankDto bank) {
//        log.info("associateUserToBank - start");
//        User userToAddBank = userFacade.associateUserToBank(userId, bank);
//        return userToAddBank;
//    }
    @PatchMapping(path = "/{user-id}/bank/{bank-id}")
    public String associateUserToBank(@PathVariable("user-id") Integer userId, @PathVariable("bank-id") Integer bankId) {
        log.info("associateUserToBank - start");
        String  associatedUserToBank = userFacade.associateUserToBank(userId, bankId);
        log.info("associateUserToBank - user with id {} assigned to the bank with id {}", userId, bankId);
        return associatedUserToBank;
    }
}
