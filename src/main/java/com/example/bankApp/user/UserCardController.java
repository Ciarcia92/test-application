package com.example.bankApp.user;

import com.example.bankApp.bank.BankService;
import com.example.bankApp.creditCard.CardService;
import com.example.bankApp.creditCard.CardResponseDto;
import com.example.bankApp.user.UserResponseDto;
import com.example.bankApp.bank.BankRepository;
import com.example.bankApp.creditCard.CardFacade;
import com.example.bankApp.user.UserFacade;
import com.example.bankApp.creditCard.CreditCard;
import com.example.bankApp.user.User;
import com.example.bankApp.user.UserRepository;
import com.example.bankApp.bank.BankMapper;
import com.example.bankApp.user.UserMapper;
import com.example.bankApp.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/card")
public class UserCardController {

    private final UserFacade userFacade;
    private final CardFacade cardFacade;

    public UserCardController(UserService userService, UserRepository userRepository, UserMapper userMapper, UserFacade userFacade, CardFacade cardFacade, BankService bankService, BankMapper bankMapper, BankRepository bankRepository, CardService cardService) {
        this.userFacade = userFacade;
        this.cardFacade = cardFacade;
    }

    //    get the user associated with a card
    @GetMapping(path = "/{card-id}/user/dto", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDto getUserDtoByCardId(@PathVariable("card-id") Integer id) {
        log.info("getUserDtByCardId - start");
        UserResponseDto user = userFacade.getUserDtoByCardId(id);
        log.info("getUserDtoByCardId - success, found user with id {}", id);
        return user;
    }
    @GetMapping(path = "/{card-id}/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserByCardId(@PathVariable("card-id") Integer id) {
        log.info("getUserByCardId - start");
        User user = userFacade.getUserByCardId(id);
        log.info("getUserByCardId - success, found user with id {}", id);
        return user;
    }
    @GetMapping(path = "/iban/{iban}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDto getUserByCardIban(@PathVariable("iban") String iban) {
        log.info("getUserByCardIban - start");
        UserResponseDto user = userFacade.getUserByCardIban(iban);
        log.info("getUserByCardIban - success, found user with iban {}", iban);
        return user;
    }

    // get all the cards associated with a user
    @GetMapping(path = "/user/{user-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CreditCard> getCardsByUserId(@PathVariable("user-id") Integer id) {
        log.info("getCardsByUserId - start");
        List<CreditCard> cardList = cardFacade.getCardsByUserId(id);
        log.info("getCardsByUserId - success, found {} cards for user with id {}", cardList.size(), id);
        return cardList;
    }
    @GetMapping(path = "/dto/user/{user-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CardResponseDto> getCardsDtoByUserId(@PathVariable("user-id") Integer id) {
        log.info("getCardsDtoByUserId - start");
        List<CardResponseDto> cardList = cardFacade.getCardsDtoByUserId(id);
        log.info("getCardsDtoByUserId - success, found {} cards for user with id {}", cardList.size(), id);
        return cardList;
    }

//    USELESS BCS THE CARD IS ASSOCIATED TO THE USER WHEN HE MAKE THE REUEST FOR THE CARD
//    //associate user with card
//    @PatchMapping(path = "/{card-id}/user/{user-id}")
//    public String associateUserToCard(@PathVariable("card-id") Integer cardId, @PathVariable("user-id") Integer userId) {
//        log.info("associateUserToCard - start");
//        String associateUserToCard = userFacade.associateUserToCard(userId, cardId);
//        log.info("associateUserToCard - card with id {} assigned to the user with id {}", cardId, userId);
//        return associateUserToCard;
//    }
//
//    @PatchMapping(path = "/{user-id}/card/{card-id}")
//        public User associateCardToUser(@PathVariable("user-id") Integer userId, @PathVariable("card-id") Integer cardId) {
//        log.info("addCardToUser - start");
//        User cardToAddCard = userFacade.associateUserToCard(userId, card);
//        log.info("addCardToUser - card with id {} assigned to the user with id {}", card.getId(), userId);
//        return cardToAddCard;
//    }



    //    get all the bank associated with a fiscal code
//    @GetMapping(path = "/{fiscal-code}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<CardResponseDto> getCardByUserFiscalCode(@PathVariable("fiscal-code") String fiscalCode) {
//        log.info("getCardByUserFiscalCode - start");
//        List<CardResponseDto> bankList = bankFacade.getCardsByUserFiscalCode(fiscalCode);
//        log.info("getCardByUserFiscalCode - success, found {} banks associated to the card with fiscal code {}", bankList.size(), fiscalCode);
//        return bankList;
//    }
//
//    //    get all the banks a card is associated with
//    @GetMapping(path = "/{card-id}/bank", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<CardResponseDto> getCardByUserId(@PathVariable("card-id") Integer id) {
//        log.info("getCardByUserId - start");
//        List<CardResponseDto> bankList = bankFacade.getCardsByUserId(id);
//        log.info("getCardByUserId - success, found {} banks for card with id {}", bankList.size(), id);
//        return bankList;
//    }

//

}
