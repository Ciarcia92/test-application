package com.example.bankApp.transaction;

import com.example.bankApp.creditCard.CreditCard;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Transaction {
    @Id
    @GeneratedValue
    private Integer id;

    private BigDecimal amount;

    @CreatedDate
    private LocalDateTime creationDate;

    private String paymentDescription;

    @ManyToOne
    @JoinColumn(name = "creditCard_id")
    @JsonBackReference
//    @JsonIgnore
    private CreditCard creditCard;

}
