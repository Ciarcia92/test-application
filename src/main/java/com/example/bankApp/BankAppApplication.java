package com.example.bankApp;

import com.example.bankApp.bank.Bank;
import com.example.bankApp.user.User;
import com.example.bankApp.embedded.Address;
import com.example.bankApp.role.Role;
import com.example.bankApp.role.RoleRepository;
import com.example.bankApp.bank.BankRepository;
import com.example.bankApp.user.UserRepository;
import com.example.bankApp.bank.BankService;
import com.example.bankApp.user.UserService;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
public class BankAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankAppApplication.class, args);
    }

//    @Bean
    public CommandLineRunner commandLineRunner(
            RoleRepository roleRepository,
            BankRepository bankRepository,
            BankService bankService,
            UserRepository userRepository,
            UserService userService,
            PasswordEncoder passwordEncoder) {
        return args -> {
            Role employeRole = roleRepository.findByName("EMPLOYEE")
                    .orElseGet(() -> roleRepository.save(Role.builder().name("EMPLOYEE").build()));
            Role clientRole = roleRepository.findByName("CLIENT")
                    .orElseGet(() -> roleRepository.save(Role.builder().name("CLIENT").build()));

            for (int i = 0; i < 10; i++) {
                Faker faker = new Faker();

                Address address = Address.builder()
                        .buildingNumber(faker.address().buildingNumber())
                        .street("via " + faker.address().streetName())
                        .zipCode(faker.address().zipCode())
                        .build();

                Bank bank = Bank.builder()
                        .address(address)
                        .name("Bank " + faker.company().name() + "of Rome")
                        .bankNumberIdentifier(bankService.generateBankIdentificationNumber())
                        .users(new ArrayList<User>())
                        .build();
                bankRepository.save(bank);

                User user = User.builder()
                        .address(address)
                        .fiscalCode(userService.generateFiscalCode())
                        .email(faker.name().username() + "@email.com")
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .dateOfBirth(faker.date().between(
                                        java.sql.Date.valueOf(LocalDate.of(1990, 01, 01)),
                                        java.sql.Date.valueOf(LocalDate.of(2000, 12, 12)))
                                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
//                        .created(LocalDateTime.now())
                        .password(passwordEncoder.encode("password123"))
                        .banks(new ArrayList<Bank>())
                        .enabled(true)
                        .roles(List.of(employeRole))
                        .build();

                userRepository.save(user);
                user.getBanks().add(bank);
                bank.getUsers().add(user);
                bankRepository.save(bank);

                userRepository.save(user);
            }

        };
    }
//    @Bean
//    public CommandLineRunner commandLineRunner(
//            BankRepository bankRepository,
//            BankService bankService,
//            UserRepository userRepository,
//            UserService userService,
//            CardRepository cardRepository,
//            CardService cardService,
//            TransactionRepository transactionRepository
//    ) {
//        return args -> {
//            for (int i = 0; i < 10; i++) {
//                Faker faker = new Faker();
//
//                Address address = Address.builder()
//                        .buildingNumber(faker.address().buildingNumber())
//                        .street("via " + faker.address().streetName())
//                        .zipCode(faker.address().zipCode())
//                        .build();
//
//                Bank bank = Bank.builder()
//                        .address(address)
//                        .name("Bank " + faker.company().name())
//                        .bankNumberIdentifier(bankService.generateBankIdentificationNumber())
//                        .build();
//                bankRepository.save(bank);
//                User user = User.builder()
//                        .address(address)
//                        .fiscalCode(userService.generateFiscalCode())
//                        .email(faker.name().username() + "@email.com")
//                        .firstName(faker.name().firstName())
//                        .lastName(faker.name().lastName())
//                        .dateOfBirth(faker.date().between(
//                                        java.sql.Date.valueOf(LocalDate.of(1990, 01, 01)),
//                                        java.sql.Date.valueOf(LocalDate.of(2000, 12, 12)))
//                                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
////                        .created(LocalDateTime.now())
//                        .build();
//
////                userRepository.save(user);
//
//
////                userRepository.save(user);
////                bankRepository.save(bank);
//
//                CreditCard card = CreditCard.builder()
//                        .iban(cardService.generateIban())
//                        .cardNumber(cardService.generateCardNumber())
//                        .dateOfRelease(faker.date().between(
//                                        java.sql.Date.valueOf(LocalDate.of(2020, 01, 31)),
//                                        java.sql.Date.valueOf(LocalDate.of(2024, 12, 31)))
//                                .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
//                        .build();
//                cardRepository.save(card);
//                user.setBanks(new ArrayList<>());
//                user.getBanks().add(bank);
//
//                bank.getUsers().add(user);
//                bank.getCards().add(card);
//
//             card.setUser(user);
//             card.setBank(bank);
//
//                user.getCards().add(card);
//
//
//                userRepository.save(user);
//                bankRepository.save(bank);
//
//                Transaction transaction = Transaction.builder()
//                        .amount(faker.number().numberBetween(100, 10000))
//                        .creationDate(faker.date().between(
//                                        java.sql.Date.valueOf(LocalDate.of(2021, 01, 31)),
//                                        java.sql.Date.valueOf(LocalDate.of(2024, 12, 31)))
//                                .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
//                        .paymentDescription("")
//                        .creditCard(card)
//                        .build();
//                transactionRepository.save(transaction);
//
//                card.setTransactions(new ArrayList<>());
//                card.getTransactions().add(transaction);
//
//                cardRepository.save(card);
//
//            }
//        };
//
//    }


    //
    //    public Bank getBank(Integer id) {
    //        return bankRepository.findById(id).orElseThrow(
    //                () -> {
    //                    String errorMsg = String.format("Bank with id %d not found", id);
    //                    log.warn(errorMsg);
    //                    return new ResourceNotFoundException(errorMsg);
    //                }
    //        );
    //    }
    //
    //    public BankResponseDto getBankDtoById(Integer id) {
    //        return bankRepository.findById(id).map(bank -> bankMapper.toBankResponseDto(bank))
    //                .orElseThrow(
    //                        () -> {
    //                            String errorMsg = String.format("Bank with id %d not found", id);
    //                            log.warn(errorMsg);
    //                            return new ResourceNotFoundException(errorMsg);
    //                        }
    //                );
    //    }
    //
    //    public Bank addBank(Bank newBank) {
    //        Optional<Bank> bankToSave = bankRepository.findById(newBank.getId());
    //        if (bankToSave.isPresent()) {
    //            String errorMsg = String.format("Bank with id %s already present", newBank.getId());
    //            log.warn(errorMsg);
    //            throw new DuplicateResourceException(errorMsg);
    //        }
    //        return bankRepository.save(newBank);
    //    }


}
