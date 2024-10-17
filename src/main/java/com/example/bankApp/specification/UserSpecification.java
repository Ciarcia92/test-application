package com.example.bankApp.specification;

import com.example.bankApp.user.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> hasEmail(String email) {
        return (Root<User> root,
        CriteriaQuery<?> query,
        CriteriaBuilder builder) -> {
            if(email == null || email.isEmpty()) {
                return null;
            }
            return builder.equal(root.get("email"), email);
        };
    }

}
