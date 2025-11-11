package com.example.SmartSpendExpense.repository;

import com.example.SmartSpendExpense.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
    Users findByUsername(String username);

    Users findByEmail(String mail);
}
