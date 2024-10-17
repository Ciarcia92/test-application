package com.example.bankApp.creditCard;

import com.example.bankApp.exceptions.ResourceNotFoundException;
import com.example.bankApp.user.User;
import com.example.bankApp.bank.BankRepository;
import com.example.bankApp.user.UserRepository;
import com.example.bankApp.bank.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CardFacade {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;

    public CardFacade(CardRepository cardRepository, CardMapper cardMapper, UserRepository userRepository, BankService bankService, BankRepository bankRepository) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
        this.userRepository = userRepository;
        this.bankRepository = bankRepository;
    }

    public List<CreditCard> getCardsByUserId(Integer id) {
        userRepository.findById(id)
                .orElseThrow(
                        () -> {
                            String errorMsg = String.format("User with id %d not found", id);
                            log.warn(errorMsg);
                            return new ResourceNotFoundException(errorMsg);
                        });
        List<CreditCard> cards = cardRepository.findCardsByUserId(id);
        if(cards.isEmpty()) {
            log.warn("No credit card found for user with id {}", id);
        }
        return cards;
    }

    public List<CardResponseDto> getCardsDtoByUserId(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> {
                            String errorMsg = String.format("User with id %d not found", id);
                            log.warn(errorMsg);
                            return new ResourceNotFoundException(errorMsg);
                        });
        List<CreditCard> cards = cardRepository.findCardsByUserId(id);
        if(cards.isEmpty()) {
            log.warn("No credit card found for user with id {}", id);
        }
        return cards.stream().map(cardMapper::toCardResponseDto)
                .collect(Collectors.toList());
    }

    public List<CreditCard> getCardsByBankId(Integer id) {
        bankRepository.findById(id).orElseThrow(
                () -> {
                    String errorMsg = String.format("Bank with id %d not found", id);
                    log.warn(errorMsg);
                    return new ResourceNotFoundException(errorMsg);
                });
        List<CreditCard> cards  = cardRepository.findCardsByBankId(id);
        if(cards.isEmpty()) {
            log.warn("No credit card found for bank with id {}", id);
        }
        return cards;
    }

    //    per passare createdBy non è necessario passare Authentication come parametro
//    public Integer addCreditCard(CardRequest request, Authentication connectedUser) {
//        User user = ((User) connectedUser.getPrincipal());
//        CreditCard card = cardMapper.toCard(request);
//        return cardRepository.save(card).getId();
//    }
//    public String associate
}
