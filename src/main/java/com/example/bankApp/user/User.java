package com.example.bankApp.user;

import com.example.bankApp.bank.Bank;
import com.example.bankApp.creditCard.CreditCard;
import com.example.bankApp.embedded.Address;
import com.example.bankApp.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.persistence.FetchType.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails, Principal {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private boolean accountLocked;

    private boolean enabled;

    @NotNull
    private LocalDate dateOfBirth;

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String fiscalCode;

    @Embedded
    private Address address;

    @ManyToMany(fetch = EAGER)
    private List<Role> roles;

    @ManyToMany(mappedBy = "users")
//    @JsonBackReference("bank-user")
    @JsonIgnore
    @Builder.Default
    private List<Bank> banks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
//    @JsonManagedReference("user-card")
    @JsonIgnore
//    @Builder.Default
    private List<CreditCard> cards = new ArrayList<>();


    @Override
    public String getName() {
        return email;
    }

    public String getFullName() {
            return firstName + " " + lastName;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", dateOfBirth=" + dateOfBirth +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';    }

}
