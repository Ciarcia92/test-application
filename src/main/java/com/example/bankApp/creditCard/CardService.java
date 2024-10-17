package com.example.bankApp.creditCard;

import com.example.bankApp.exceptions.DuplicateResourceException;
import com.example.bankApp.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public CardService(CardRepository cardRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
    }
    public List<CreditCard> getAllCard() {
        List<CreditCard> cardList = cardRepository.findAll();
        if (cardList.isEmpty()) {
            log.warn("No cards found");
            throw new ResourceNotFoundException("No cards found");
        }
        return cardList;
    }
    public List<CardResponseDto> getAllCardsDto() {
        List<CardResponseDto> cardList = cardRepository.findAll()
                .stream().map(cardMapper::toCardResponseDto)
                .collect(Collectors.toList());
        if (cardList.isEmpty()) {
            log.warn("No cards found");
            throw new ResourceNotFoundException("No cards found");
        }
        return cardList;
    }
    public CreditCard getCardById(Integer id) {
        return cardRepository.findById(id).orElseThrow(
                () -> {
                    String errorMsg = String.format("Card with ID %d not found", id);
                    log.warn(errorMsg);
                    return new ResourceNotFoundException(errorMsg);
                });
    }
    public CardResponseDto getCardDtoById(Integer id) {
        return cardRepository.findById(id).map(cardMapper::toCardResponseDto).orElseThrow(
                () -> {
                    String errorMsg = String.format("Card with ID %d not found", id);
                    log.warn(errorMsg);
                    return new ResourceNotFoundException(errorMsg);
                });
    }

    public CreditCard getCardByCardNumber(BigInteger cardNumber) {
        return cardRepository.findCreditCardByCardNumber(cardNumber)
                .orElseThrow(() -> {
                    String errorMsg = String.format("Credit card number %s not found", cardNumber);
                    log.warn(errorMsg);
                    return new ResourceNotFoundException(errorMsg);
                });
    }
    public CreditCard getCardByCardIban(String iban) {
        return cardRepository.findCreditCardByIban(iban)
                .orElseThrow(() -> {
                    String errorMsg = String.format("Credit card with iban %s not found", iban);
                    log.warn(errorMsg);
                    return new ResourceNotFoundException(errorMsg);
                });
    }
    public CreditCard addCreditCard(CreditCard newCard) {
        Optional<CreditCard> existingCard = cardRepository.findCreditCardByCardNumber(newCard.getCardNumber());
        if (existingCard.isPresent()) {
            String errorMsg = String.format("Card with card number %s already present", newCard.getCardNumber());
            log.warn(errorMsg);
            throw new DuplicateResourceException(errorMsg);
        }
        return cardRepository.save(newCard);
    }
    public CardResponseDto addCardResponseDto(CardDto newCard) {
        Optional<CreditCard> existingCard = cardRepository.findCreditCardByCardNumber(newCard.cardNumber());
        if (existingCard.isPresent()) {
            String errorMsg = String.format("Card with card number %s already present", newCard.cardNumber());
            log.warn(errorMsg);
            throw new DuplicateResourceException(errorMsg);
        }
        CreditCard card = cardMapper.toCreditCard(newCard);
        CreditCard savedCard = cardRepository.save(card);
        return cardMapper.toCardResponseDto(savedCard);
    }
    public void deleteCardById(Integer id) {
        CreditCard card = cardRepository.findById(id).orElseThrow(
                () -> {
                    String errorMsg = String.format("Card with ID %d not found", id);
                    log.warn(errorMsg);
                    return new ResourceNotFoundException(errorMsg);
                });
        cardRepository.deleteById(id);
        log.info("deleteCardById - deleted card with id {}", id);
    }
    public void deleteCardByCardNumber(BigInteger cardNumber) {
        CreditCard card = cardRepository.findCreditCardByCardNumber(cardNumber)
                .orElseThrow(() -> {
                    String errorMsg = String.format("Credit card number %s not found", cardNumber);
                    log.warn(errorMsg);
                    return new ResourceNotFoundException(errorMsg);
                });
        cardRepository.delete(card);
        log.info("deleteCardById - deleted card with card number {}", cardNumber);
    }

    public BigInteger generateCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return new BigInteger(cardNumber.toString());
    }

    public String generateIban() {
        Random random = new Random();
        StringBuilder iban = new StringBuilder();
        iban.append("IT");
        for (int i = 0; i < 2; i++) {
            iban.append(random.nextInt(10));
        }
        iban.append((char) ('A'+ random.nextInt(26)));
        for (int i = 0; i < 21; i++) {
            iban.append(random.nextInt(10));
        }
        return iban.toString();
    }

    public String generatePin() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder pin = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            pin.append(secureRandom.nextInt(10));
        }
        return pin.toString();
    }

    public String generateAndEncrytPin(String pin) {
//        String pin = generatePin();
        return encryptPin(pin);
    }
    public String encryptPin(String pin) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(pin);
    }
}
