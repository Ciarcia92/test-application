package com.example.bankApp.creditCard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/card")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CreditCard> getCreditCards() {
        log.info("getCreditCards - start");
        List<CreditCard> cards = cardService.getAllCard();
        log.info("getCreditCards - success, found {} cards", cards.size());
        return cards;
    }

    @GetMapping(path = "/{card-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CreditCard getCreditCard(
            @PathVariable("card-id") Integer id) {
        log.info("getCreditCard - start");
        CreditCard card = cardService.getCardById(id);
        log.info("getCreditCard - success, card with id {} found", id);
        return card;
    }

    @GetMapping(path = "/dto/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CardResponseDto getCreditCardDtoById(
            @PathVariable("id") Integer id
    ) {
        log.info("getCreditCardDto - start");
        CardResponseDto cardResponseDto = cardService.getCardDtoById(id);
        log.info("getCreditCardDto - success, card with id {} found", id);
        return cardResponseDto;
    }

    @GetMapping(path = "/card-number/{card-number}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CreditCard getCreditCardByCardNumber(
            @PathVariable("card-number") BigInteger cardNumber
    ) {
        log.info("getCreditCardByCardNumber - start");
        CreditCard card = cardService.getCardByCardNumber(cardNumber);
        log.info("getCreditCardByCardNumber - success, card with fiscal code {} found", cardNumber);
        return card;
    }

    @GetMapping(path = "/dto", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CardResponseDto> getAllCreditCardsDto() {
        log.info("getAllCreditCardsDto - start");
        List<CardResponseDto> cardResponseDtos = cardService.getAllCardsDto();
        log.info("getAllCreditCardsDto - success, found {} cardDto,", cardResponseDtos.size());
        return cardResponseDtos;
    }

//    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.CREATED)
//    public CreditCard addCreditCard(@Valid @RequestBody CreditCard newCreditCard) {
//        log.info("addCreditCard - start");
//        CreditCard savedCreditCard = cardService.addCreditCard(newCreditCard);
//        log.info("addCreditCard - success, card with id {} created", savedCreditCard.getId());
//        return savedCreditCard;
//    }
//    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.CREATED)
//    public CreditCard addCreditCard(@Valid @RequestBody CreditCard newCreditCard) {
//        log.info("addCreditCard - start");
//        CreditCard savedCreditCard = cardService.addCreditCard(newCreditCard);
//        log.info("addCreditCard - success, card with id {} created", savedCreditCard.getId());
//        return savedCreditCard;
//    }

    @PostMapping(path = "/dto/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CardResponseDto addCreditCardDto(@RequestBody CardDto cardDto) {
        log.info("addCreditCardDto - start");
        CardResponseDto cardResponseDto = cardService.addCardResponseDto(cardDto);
        log.info("addCreditCardDto - card with card number {} created",  cardDto.cardNumber());
        return cardResponseDto;
    }

    @DeleteMapping(path = "/{card-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCreditCard(@PathVariable("card-id") Integer id) {
        log.info("deleteCreditCard - start - deleting card with id {}", id);
        cardService.deleteCardById(id);
        log.info("deleteCreditCard - success - deleted card with id {}", id);
    }
    @DeleteMapping(path = "/number/{card-number}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCreditCardByCardNumber(@PathVariable("card-number") BigInteger cardNumber) {
        log.info("deleteCreditCardByCardNumber - start - deleting card with fiscal code {}", cardNumber);
        cardService.deleteCardByCardNumber(cardNumber);
        log.info("deleteCreditCardByCardNumber - success - deleted card with fiscal code {}", cardNumber);
    }
}
