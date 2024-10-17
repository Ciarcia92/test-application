package com.example.bankApp.creditCard;

import com.example.bankApp.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<CreditCard, Integer>, JpaSpecificationExecutor<CreditCard> {

    Optional<CreditCard> findCreditCardByCardNumber(BigInteger cardNumber);

    Optional<CreditCard> findCreditCardByIban(String iban);

    @Query("SELECT c.user FROM CreditCard c WHERE c.id = :id")
    Optional<User> findUserByCardId(@Param("id") Integer id);

    @Query("SELECT c.user FROM CreditCard c WHERE c.cardNumber = :cardNumber")
    Optional<User> findUserByCardNumber(@Param("cardNumber") Long cardNumber);

    @Query("SELECT c.user FROM CreditCard c WHERE c.iban = :iban")
    Optional<User> findUserByCardIban(@Param("iban") String iban);

    @Query("SELECT c FROM CreditCard c WHERE c.user.id = :userId")
    List<CreditCard> findCardsByUserId(@Param("userId") Integer userId);

    @Query("SELECT c FROM CreditCard c WHERE c.bank.id = :bankId")
    List<CreditCard> findCardsByBankId(@Param("bankId") Integer bankId);


}
