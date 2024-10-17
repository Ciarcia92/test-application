package com.example.bankApp.bank;

import com.example.bankApp.creditCard.CreditCard;
import com.example.bankApp.creditCard.CreditCardRequest;
import com.example.bankApp.user.User;
import com.example.bankApp.embedded.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "banks")
@Builder
public class Bank implements Principal {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank
    @Column(unique = true, length = 50)
    private String name;

    @Embedded
    private Address address;

    @NotBlank
    @Column(unique = true)
    private String bankNumberIdentifier;

    @ManyToMany
    @JoinTable(name = "bank_user",
    joinColumns = {
            @JoinColumn(name = "bank_id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "user_id")
    })
    @Builder.Default
//    @JsonManagedReference("bank-user")
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "bank")
    @JsonManagedReference("bank-card")
    @Builder.Default
    private List<CreditCard> cards = new ArrayList<>();

    @OneToMany(mappedBy = "bank")
    @JsonManagedReference("bank-request")
    @Builder.Default
    private List<CreditCardRequest> creditCardRequests = new ArrayList<>();
}
