package com.example.bankApp.transaction;

import com.example.bankApp.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query("SELECT t FROM Transaction t WHERE t.creditCard.id = :cardId")
    List<Transaction> findTransactionByCardId(@Param("cardId") Integer cardId);

    @Query("SELECT t FROM Transaction t WHERE DATE(t.creationDate) = :creationDate")
    List<Transaction> findTransactionByCreationDate(@Param("creationDate") LocalDateTime creationDate);

    @Query("SELECT t FROM Transaction t JOIN t.creditCard c JOIN c.user u where u.id = :userId")
    List<Transaction> findTransactionByUserId(@Param("userId") Integer id);

}
