package com.example.bankApp.bank;

import com.example.bankApp.creditCard.CreditCard;
import com.example.bankApp.creditCard.CardFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/card")
public class BankCardController {

   private final CardFacade cardFacade;

    public BankCardController(CardFacade cardFacade) {
        this.cardFacade = cardFacade;
    }

    // get all the cards issued from a bank
    @GetMapping(path = "/bank/{bank-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CreditCard> getCardsByBankId(@PathVariable("bank-id") Integer id) {
        log.info("getCardsByBankId - start");
        List<CreditCard> cardList = cardFacade.getCardsByBankId(id);
        log.info("getCardsByUserId - success, found {} cards for bank with id {}", cardList.size(), id);
        return cardList;
    }

//    per passare createdBy non è necessario passare Authentication come parametro
//    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Integer> addCreditCard(@Valid @RequestBody CardRequest newCreditCard, Authentication connectedUser) {
//        log.info("addCreditCard - star - bank {} is creating a card", connectedUser.getName());
//        Integer savedCreditCard = cardFacade.addCreditCard(newCreditCard, connectedUser);
//        log.info("addCreditCard - success ");
//        return ResponseEntity.ok(cardFacade.addCreditCard(newCreditCard, connectedUser));
////        return ResponseEntity.status(HttpStatus.CREATED).body(savedCreditCard.getId());
//    }

}
