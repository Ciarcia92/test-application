package com.example.bankApp.creditCard;

import com.example.bankApp.bank.Bank;
import com.example.bankApp.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CreditCardRequest {

    @Id
    @GeneratedValue
    private Integer id;

//    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "client")
    private User client;

    @ManyToOne
    @JoinColumn(name = "bank")
    @JsonBackReference("bank-request")
    private Bank bank;

    private String status;

    @CreatedDate
    private LocalDateTime requestDate;

}
