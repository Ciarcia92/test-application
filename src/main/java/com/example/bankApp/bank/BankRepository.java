package com.example.bankApp.bank;

import com.example.bankApp.embedded.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {

    Optional<Bank> findBankByBankNumberIdentifier(String bankNumberIdentifier);
    Optional<Bank> findBankByAddress(Address address);

    @Transactional
    void deleteByBankNumberIdentifier(String identificationNumber) ;

    @Query("SELECT b FROM Bank b JOIN b.users u where u.fiscalCode = :fiscalCode")
    List<Bank> findBankByUserFiscalCode(@Param("fiscalCode") String fiscalCode);
}
