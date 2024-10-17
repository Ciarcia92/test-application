package com.example.bankApp.creditCard;

import com.example.bankApp.user.User;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CreditCardRequestController {

    private final CreditCardRequestService creditCardRequestService;


    @PostMapping("/request")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<String> requestCreditCard(
            @RequestBody CreditCardRequestDto cardRequestDto,
            Authentication authentication) throws MessagingException {
        log.info("Credit card request issued");
        User client = (User) authentication.getPrincipal();
        log.info("Authenticated user {}", client.getFullName());
        String response = creditCardRequestService.createCreditCardRequest(client, cardRequestDto);
    return  ResponseEntity.ok(response);
    }

    @GetMapping("/request/pending")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<CreditCardRequest>> getPendingRequest(Authentication authentication) {
        log.info("Retrieving credit card requests");
        User employee = (User) authentication.getPrincipal();
        log.info("Authenticated user {}", employee.getFullName());
        List<CreditCardRequest> requests = creditCardRequestService.getPendingRequestsForEmployee(employee);
        return  ResponseEntity.ok(requests);
    }

    @PostMapping("/request/{requestId}/approve")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<String> approveRequest(
            @PathVariable Integer requestId, Authentication authentication) {
        log.info("Received credit card request with id: {}", requestId);
        User employee = (User) authentication.getPrincipal();
        log.info("Authenticated user {}", employee.getFullName());
        creditCardRequestService.approveRequest(requestId, employee);
        return  ResponseEntity.ok("Request approved and credit card created.");
    }
}
