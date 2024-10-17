package com.example.bankApp.bank;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bank")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }


    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Bank> getAllBank() {
        log.info("getAllBank - start");
        List<Bank> bankList = bankService.getAllBank();
        log.info("getAllBank - success, found {} banks", bankList.size());
        return bankList;
    }

    @GetMapping(path = "/dto", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BankResponseDto> getAllBankDto() {
        log.info("getAllBankDto - start");
        List<BankResponseDto> bankResponseDtos = bankService.getAllBankDto();
        log.info("getAllBankDto - success, found {} dto", bankResponseDtos.size());
        return bankResponseDtos;
    }

    @GetMapping(path = "/dto/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BankResponseDto getBankDtoById(@PathVariable("id") Integer id) {
        log.info("getBankDto - start");
        BankResponseDto bankResponseDto = bankService.getBankDtoById(id);
        log.info("getBankDto - success, bank with id {} found", id);
        return bankResponseDto;
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Bank getBank(@PathVariable("id") Integer id) {
        log.info("getBank - start");
        Bank bank = bankService.getBank(id);
        log.info("getBank- success, bank with id {} found", id);
        return bank;
    }

    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Bank addBank(@Valid @RequestBody Bank newBank) {
        log.info("addBank - start");
        Bank savedBank = bankService.addBank(newBank);
        log.info("addBank - success, bank with id {} created", savedBank.getId());
        return savedBank;
    }


    @DeleteMapping(path = "/{bank-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBank(@PathVariable("bank-id") Integer id) {
        log.info("deleteBank - start");
        bankService.deleteBankById(id);
        log.info("deleteBank - success - deleted bank with id {}", id);
    }
    @DeleteMapping(path = "/identification-number/{identification-number}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBankByIdentificationNumber(@PathVariable("identification-number") String identificationNumber) {
        log.info("deleteBank - start");
        bankService.deleteByIdentificationNumber(identificationNumber);
        log.info("deleteBank - success - deleted bank with identificationNumber {}", identificationNumber);
    }
/*
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Bank>> getAllBank() {
        log.info("getAllBank - start");
        try {
            List<Bank> bankList = bankService.getAllBank();
            if (bankList.isEmpty()) {
                log.warn("getAllBank - no bank found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            log.info("getAllBank - success, found {} banks", bankList.size());
            return new ResponseEntity<>(bankList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getAllBank - error occurred: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
    /*
    @GetMapping(path = "/dto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BankResponseDto>> getAllBankDto() {
        log.info("getAllBankDto - start");
        try {
            List<BankResponseDto> bankResponseDtos = bankService.getAllBankDto();
            if (bankResponseDtos.isEmpty()) {
                log.warn("getAllBankDto - no dto found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            log.info("getAllBankDto - success, found {} dto", bankResponseDtos.size());
            return new ResponseEntity<>(bankResponseDtos, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getAllBankDto - error occurred: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
    /*
    @GetMapping(path = "/dto/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BankResponseDto> getBankDtoById(
            @PathVariable("id") Integer id
    ) {
        log.info("getBankDto - start");
        Optional<BankResponseDto> bankResponseDto = bankService.getBankDtoById(id);
        try {
            if (bankResponseDto.isEmpty()) {
                log.warn("no bank found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            log.info("getBankDto - success, bank with id {} found", id);
            return new ResponseEntity<>(bankResponseDto.get(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("getBankDto - error occurred", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
    /*
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Bank> getBank(@PathVariable("id") Integer id) {
        log.info("getBank - start");
        try {
            Optional<Bank> bank = bankService.getBank(id);
            if (bank.isEmpty()) {
                log.warn("getBank - bank with id {} not found", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            log.info("getBank- success, bank with id {} found", id);
            return new ResponseEntity<>(bank.get(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("getBank - error occurred:", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
/*
    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Bank> addBank(@RequestBody Bank newBank) {
        log.info("addBank - start");
        try {
            Optional<Bank> existingBank = bankService.getBankByBankNumberIdentifier(newBank.getBankNumberIdentifier());
            if (existingBank.isPresent()) {
                log.warn("addBank - bank with Bank Identifier {} already present", newBank.getBankNumberIdentifier());
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            Bank savedBank = bankService.addBank(newBank);
            log.info("addBank - success, bank with id {} created", savedBank.getId());
            return new ResponseEntity<>(savedBank, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("addBank - error occurred: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
*/
    /*
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Bank> deleteBank(@PathVariable("id") Integer id) {
        log.info("deleteBank - start");
        try {
            Optional<Bank> bank = bankService.getBank(id);
            if (bank.isEmpty()) {
                log.warn("deleteBank - bank with id {} not found", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            bankService.deleteBank(id);
            log.info("deleteBank - success, bank with id {} deleted", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("deleteBank - error occurred:", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
/*
    @DeleteMapping(path = "/identification-number/{identification-number}")
    public ResponseEntity<Bank> deleteBankByIdentificationNumber(@PathVariable("identification-number") String identificationNumber) {
        log.info("deleteBank - start");
        try {
            Optional<Bank> bank = bankService.getBankByBankNumberIdentifier(identificationNumber);
            if (bank.isEmpty()) {
                log.warn("deleteBank - bank with identification number {} not found", identificationNumber);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            bankService.deleteByIdentificationNumber(identificationNumber);
            log.info("deleteBank - success, bank with identification number {} deleted", identificationNumber);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("deleteBank - error occurred:", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/
}
