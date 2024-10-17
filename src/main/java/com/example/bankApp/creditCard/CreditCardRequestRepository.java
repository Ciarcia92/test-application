package com.example.bankApp.creditCard;

import com.example.bankApp.bank.Bank;
import com.example.bankApp.creditCard.CreditCardRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRequestRepository extends JpaRepository<CreditCardRequest, Integer> {

    List<CreditCardRequest> findByBankInAndStatus(List<Bank> bank, String status);

//    @Query("SELECT c FROM CreditCardRequest c WHERE c.bank.id = :bankId and c.status = :status")
//    List<CreditCardRequest> findCreditCardRequestByBankAndStatus(@Param("bankId")Bank bank, @Param("status") String status);

}
