package com.example.bankApp.creditCard;

import com.example.bankApp.bank.Bank;
import com.example.bankApp.user.User;
import com.example.bankApp.transaction.Transaction;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credit_cards")
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CreditCard {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "IBAN", unique = true)
    private String iban;

    @Column(unique = true)
    private BigInteger cardNumber;

    @Column(name = "PIN", unique = true, nullable = false)
    private String pin;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateOfRelease;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private Integer createdBy;


    @OneToMany(
            mappedBy = "creditCard", fetch = FetchType.LAZY
    )
    @JsonManagedReference
//    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "owner_id")
//    @JsonBackReference("user-card")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    @JsonBackReference("bank-card")
    private Bank bank;

    public CreditCard(Integer id, String iban, Long aLong, LocalDateTime localDateTime) {
    }
}
