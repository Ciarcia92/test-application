package com.example.bankApp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    Optional<User> findUserByFiscalCodeContaining(String fiscalCode);
    Optional<User> findUserByFiscalCode(String fiscalCode);
    Optional<User> findByEmail(String username);
    Optional<User> findUserByEmailContaining(String email);

    @Query("SELECT u FROM User u WHERE u.email = :email OR u.fiscalCode = :fiscalCode")
    Optional<User> findByEmailOrFiscalCode(@Param("email") String email, @Param("fiscalCode") String fiscalCode);

    @Query("SELECT u FROM User u JOIN u.banks b JOIN u.roles r WHERE b.id = :bankId AND r.id = 1")
    List<User> findEmployeesByBankId(@Param("bankId") Integer bankId);
}
