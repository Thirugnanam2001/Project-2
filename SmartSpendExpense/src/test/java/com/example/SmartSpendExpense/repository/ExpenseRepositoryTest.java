package com.example.SmartSpendExpense.repository;

import com.example.SmartSpendExpense.model.Expense;
import com.example.SmartSpendExpense.model.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ExpenseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUserId() {
        // Given
        Users user = new Users();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");
        Users savedUser = entityManager.persistAndFlush(user);

        Expense expense = new Expense();
        expense.setTitle("Test Expense");
        expense.setAmount(new BigDecimal("100.00"));
        expense.setCategory("Food");
        expense.setType("Expense");
        expense.setDate(LocalDate.now());
        expense.setDescription("Test description");
        expense.setUser(savedUser);

        entityManager.persistAndFlush(expense);

        // When
        List<Expense> expenses = expenseRepository.findByUserId(savedUser.getId());

        // Then
        assertThat(expenses).isNotEmpty();
        assertThat(expenses.get(0).getTitle()).isEqualTo("Test Expense");
        assertThat(expenses.get(0).getUser().getId()).isEqualTo(savedUser.getId());
    }
}



//package com.example.SmartSpendExpense.repository;
//
//import com.example.SmartSpendExpense.model.Expense;
//import com.example.SmartSpendExpense.model.Users;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//class ExpenseRepositoryTest {
//
//    @Autowired
//    private ExpenseRepository expenseRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//    // void testFindByUserId()
//    @Test
//    void testFindByUserId(){
//        Users user = new Users();
//        user.setUsername("john");
//        user.setPassword("pass");
//        user.setEmail("john@example.com");
//        userRepository.save(user);
//
//        Expense expense = new Expense();
//        expense.setTitle("Groceries");
//        expense.setAmount(BigDecimal.valueOf(100));
//        expense.setCategory("Food");
//        expense.setType("Debit");
//        expense.setDate(LocalDate.now());
//        expense.setDescription("Weekly groceries");
//        expense.setUser(user);
//        expenseRepository.save(expense);
//
//        List<Expense> expenses = expenseRepository.findByUserId(user.getId());
//        assertThat(expenses).hasSize(1);
//        assertThat(expenses.get(0).getTitle()).isEqualTo("Groceries");
//    }
//}