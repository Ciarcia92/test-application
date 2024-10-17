package com.example.bankApp.creditCard;

import com.example.bankApp.email.EmailService;
import com.example.bankApp.exceptions.EmailNotificationException;
import com.example.bankApp.exceptions.OperationNotPermittedException;
import com.example.bankApp.exceptions.ResourceNotFoundException;
import com.example.bankApp.bank.Bank;
import com.example.bankApp.user.User;
import com.example.bankApp.user.UserRepository;
import com.example.bankApp.bank.BankRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditCardRequestService {
    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final CreditCardRequestRepository creditCardRequestRepository;
    private final CardService cardService;
    private final CardRepository cardRepository;
    private final EmailService emailService;

    @Transactional
    public String createCreditCardRequest(@NotNull User client, @NotNull CreditCardRequestDto cardRequestDto) throws MessagingException {
        log.info("Starting credit card requests from client {}", client.getFullName());

        User loadedClient = userRepository.findById(client.getId()).orElseThrow(
                () -> {
                    String errorMsg = String.format("Client with id %d not found", client.getId());
                    log.warn(errorMsg);
                    return new ResourceNotFoundException(errorMsg);
                }
        );

        Bank bank = bankRepository.findById(cardRequestDto.bankId()).orElseThrow(
                () -> {
                    String errorMsg = String.format("Bank with id %d not found", cardRequestDto.bankId());
                    log.warn(errorMsg);
                    return new ResourceNotFoundException(errorMsg);
                });

        Hibernate.initialize(loadedClient.getBanks());

        if (loadedClient.getBanks().stream().noneMatch(b -> b.getId().equals(bank.getId()))) {
            String errorMsg = String.format("Client %s with id %d not associated with this bank", client.getFullName(), client.getId());
            log.warn(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        CreditCardRequest cardRequest = new CreditCardRequest();
        cardRequest.setClient(loadedClient);
        cardRequest.setBank(bank);
        cardRequest.setStatus("PENDING");
        cardRequest.setRequestDate(LocalDateTime.now());
        creditCardRequestRepository.save(cardRequest);

        List<User> employees = userRepository.findEmployeesByBankId(bank.getId());
        for (User employee : employees) {
            try {
                emailService.sendCreditCardRequestEmail(
                        employee.getEmail(),
                        employee.getFullName(),
                        client.getName(),
                        bank.getName()
                );
              log.info("Email sent to employee {}", employee.getFullName());
            } catch (MessagingException e) {
                String errorMsg = String.format("Failed to send email to the client %s", client.getFullName());
                log.warn(errorMsg, e);
                throw new EmailNotificationException(errorMsg,e);
            }
        }
        log.info("Request created");
        return "Credit card request submitted successfully.";
    }

    @Transactional
    public List<CreditCardRequest> getPendingRequestsForEmployee(User employee) {
        log.info("Starting retrieving credit card requests from employee {}", employee.getFullName());
        User loadedEmployee = userRepository.findById(employee.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Employee not found")
        );
        Hibernate.initialize(loadedEmployee.getBanks());
        log.info("Requests successfully retrieved ");
        return creditCardRequestRepository.findByBankInAndStatus(loadedEmployee.getBanks(), "PENDING");

    }

    @Transactional
    public void approveRequest(Integer requestId, User employee) {
        log.info("Starting approval process for request ID: {}", requestId);
        CreditCardRequest request = creditCardRequestRepository.findById(requestId).orElseThrow(
                () ->
                        new ResourceNotFoundException("Request not found."));
        User loadedEmployee = userRepository.findById(employee.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Employee not found")
        );
        Hibernate.initialize(loadedEmployee.getBanks());

        if (!loadedEmployee.getBanks().contains(request.getBank())) {
            String errorMsg = String.format("Employee %s is not associated with bank %s", employee.getUsername(), request.getBank().getName());
            log.warn(errorMsg);
            throw new OperationNotPermittedException(errorMsg);
        }

        if (request.getStatus().equals("APPROVED")) {
            String errorMsg = String.format("The credit card request with id: %d has already been approved", requestId);
            log.warn(errorMsg);
            throw new OperationNotPermittedException(errorMsg);
        }
        request.setStatus("APPROVED");
        creditCardRequestRepository.save(request);

        CreditCard creditCard = new CreditCard();
        creditCard.setUser(request.getClient());
        creditCard.setBank(request.getBank());
        creditCard.setIban(cardService.generateIban());
        creditCard.setCardNumber(cardService.generateCardNumber());
//        creditCard.setPin(cardService.generateAndEncrytPin("12345"));
        creditCard.setPin(cardService.generatePin());
//        creditCard.setPin("12345");
        cardRepository.save(creditCard);
        log.info("Successfully approved request and created credit card for user {}", request.getClient().getUsername());
    }
}
