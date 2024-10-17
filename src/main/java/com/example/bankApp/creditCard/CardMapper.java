package com.example.bankApp.creditCard;

import org.springframework.stereotype.Service;

@Service
public class CardMapper {

    public CreditCard toCard(CardRequest cardRequest) {
        return new CreditCard(
                cardRequest.id(),
                cardRequest.iban(),
                cardRequest.cardNumber(),
                cardRequest.dateOfRelease()
        );
    }
    public CreditCard toCreditCard(CardDto cardDto) {
        if (cardDto == null) {
            throw new NullPointerException("The card dto that is trying to add in null");
        }
        CreditCard card = new CreditCard();
        card.setCardNumber(cardDto.cardNumber());
        card.setDateOfRelease(cardDto.dateOfRelease());
        card.setUser(cardDto.user());
        card.setBank(cardDto.bank());
        return card;
    }
    public CardResponseDto toCardResponseDto(CreditCard card) {
        return new CardResponseDto(
                card.getUser(),
                card.getBank().getName(),
                card.getBank().getAddress()
        );
    }
}
