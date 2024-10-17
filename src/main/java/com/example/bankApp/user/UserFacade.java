package com.example.bankApp.user;

import com.example.bankApp.bank.BankService;
import com.example.bankApp.creditCard.CardService;
import com.example.bankApp.exceptions.DuplicateResourceException;
import com.example.bankApp.exceptions.ResourceNotFoundException;
import com.example.bankApp.bank.Bank;
import com.example.bankApp.creditCard.CreditCard;
import com.example.bankApp.bank.BankRepository;
import com.example.bankApp.creditCard.CardRepository;
import com.example.bankApp.bank.BankMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserFacade {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BankService bankService;
    private final BankRepository bankRepository;
    private final CardService cardService;

    private final CardRepository cardRepository;

    public UserFacade(UserService userService, UserRepository userRepository, UserMapper userMapper, BankService bankService, BankMapper bankMapper, BankRepository bankRepository, CardService cardService, CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.bankService = bankService;
        this.bankRepository = bankRepository;
        this.cardService = cardService;
        this.cardRepository = cardRepository;
    }

//    @Transactional
    public String associateUserToBank(Integer userId, Integer bankId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            String errorMsg = String.format("User with id %d not found", userId);
            log.warn(errorMsg);
            return new ResourceNotFoundException(errorMsg);
        });
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(
                        () -> {
                            String errorMsg = String.format("Bank with id %s not found", bankId);
                            log.warn(errorMsg);
                            return new ResourceNotFoundException(errorMsg);
                        });
        if (user.getBanks().contains(bank)) {
            throw new DuplicateResourceException("The user is already associated with this bank");
        }
        user.getBanks().add(bank);
        bank.getUsers().add(user);
        String responseMessage = String.format("User %s %s is now associated with the bank %s located in %s %s",
                user.getFirstName(),
                user.getLastName(),
                bank.getName(),
                bank.getAddress().getStreet(),
                bank.getAddress().getBuildingNumber());

        userRepository.save(user);
        bankRepository.save(bank);
        return responseMessage;
//        return new UserBankAssociationDto(user.getFirstName(), user.getLastName(), bank.getName(), bank.getAddress());
    }

    public String  associateUserToCard(Integer userId, Integer cardId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    String errorMsg = String.format("User with id %d not found", userId);
                    log.warn(errorMsg);
                    return new ResourceNotFoundException(errorMsg);
                });
        CreditCard card = cardRepository.findById(cardId).orElseThrow(() -> {
            String errorMsg = String.format("Card with id %d not found", cardId);
            log.warn(errorMsg);
            return new ResourceNotFoundException(errorMsg);
        });
        if (user.getCards().contains(card)) {
            throw new DuplicateResourceException("The user is already associated with this card");
        }
        user.getCards().add(card);
        card.setUser(user);
        String responseMessage = String.format("Card %s is now associated with the user %s %s (fiscal code %s)",
                cardId,
                user.getFirstName(),
                user.getLastName(),
                user.getFiscalCode());

        userRepository.save(user);
        return responseMessage;
    }


    public List<UserResponseDto> getUserByBankId(Integer id) {
        Bank bank = bankRepository.findById(id)
                .orElseThrow(
                        () -> {
                            String errorMsg = String.format("Bank with ID %d not found", id);
                            log.warn(errorMsg);
                            return new ResourceNotFoundException(errorMsg);
                        }
                );
        return bank.getUsers().stream()
                .map(userMapper::toUserResponseDto)
                .collect(Collectors.toList());
    }

    public List<UserResponseDto> getUserByBankIdentificationNumber(String identificationNumber) {
        Bank bank = bankRepository.findBankByBankNumberIdentifier(identificationNumber)
                .orElseThrow(
                        () -> {
                            String errorMsg = String.format("Bank with identificationNumber %s not found", identificationNumber);
                            log.warn(errorMsg);
                            return new ResourceNotFoundException(errorMsg);
                        }
                );
        return bank.getUsers().stream()
                .map(userMapper::toUserResponseDto)
                .collect(Collectors.toList());
    }

    public User getUserByCardId(Integer id) {
        return cardRepository.findUserByCardId(id).orElseThrow(
                () -> {
                    String errorMsg = String.format("User not found for the card with id %d", id);
                    log.warn(errorMsg);
                    return new ResourceNotFoundException(errorMsg);
                }
        );
    }

    public UserResponseDto getUserDtoByCardId(Integer id) {
        return cardRepository.findUserByCardId(id).map(userMapper::toUserResponseDto).orElseThrow(
                () -> {
                    String errorMsg = String.format("User not found for the card with id %d", id);
                    log.warn(errorMsg);
                    return new ResourceNotFoundException(errorMsg);
                }
        );
    }

    public UserResponseDto getUserByCardNumber(Long cardNumber) {
        return cardRepository.findUserByCardNumber(cardNumber).map(userMapper::toUserResponseDto).orElseThrow(
                () -> {
                    String errorMsg = String.format("User not found for the card with card number %d", cardNumber);
                    log.warn(errorMsg);
                    return new ResourceNotFoundException(errorMsg);
                }
        );
    }

    public UserResponseDto getUserByCardIban(String iban) {
        return cardRepository.findUserByCardIban(iban).map(userMapper::toUserResponseDto).orElseThrow(
                () -> {
                    String errorMsg = String.format("User not found for the card with IBAN %s", iban);
                    log.warn(errorMsg);
                    return new ResourceNotFoundException(errorMsg);
                }
        );
    }
}
