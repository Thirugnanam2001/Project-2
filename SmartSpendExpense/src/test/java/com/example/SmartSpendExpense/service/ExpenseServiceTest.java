package com.example.SmartSpendExpense.service;

import com.example.SmartSpendExpense.model.Expense;
import com.example.SmartSpendExpense.model.Users;
import com.example.SmartSpendExpense.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    public ExpenseServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveExpense() {
        Expense expense = new Expense();
        expense.setTitle("Groceries");
        expense.setAmount(BigDecimal.valueOf(50));
        expense.setDate(LocalDate.now());

        when(expenseRepository.save(expense)).thenReturn(expense);

        Expense saved = expenseService.save(expense);

        verify(expenseRepository).save(expense);
        assertThat(saved.getTitle()).isEqualTo("Groceries");
    }

    @Test
    void testFindById() {
        Expense expense = new Expense();
        expense.setId(1);
        when(expenseRepository.findById(1)).thenReturn(Optional.of(expense));

        Optional<Expense> found = expenseService.findById(1);

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(1);
    }


    @Test
    void testFindPaginatedByUser() {
        // Given
        Integer userId = 1;
        int pageNo = 1;
        int pageSize = 10;
        String sortField = "id";
        String sortDir = "asc";

        Users user = new Users();
        user.setId(userId);
        user.setUsername("testuser");

        Expense expense1 = new Expense("Groceries", new BigDecimal("100.00"), "Food", "Expense", LocalDate.now(), "Weekly groceries", user);
        Expense expense2 = new Expense("Electricity", new BigDecimal("75.00"), "Utilities", "Expense", LocalDate.now(), "Monthly bill", user);

        List<Expense> expenses = Arrays.asList(expense1, expense2);
        Page<Expense> expensePage = new PageImpl<>(expenses);

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortField).ascending());

        when(expenseRepository.findByUserId(userId, pageable)).thenReturn(expensePage);

        // When
        Page<Expense> result = expenseService.findPaginatedByUser(pageNo, pageSize, sortField, sortDir, userId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Groceries");

        verify(expenseRepository).findByUserId(userId, pageable);
    }
}
//    @Test
//    void testFindPaginatedByUser() {
//        PageRequest pageable = PageRequest.of(0, 5);
//        Page<Expense> page = new PageImpl<>(Collections.emptyList());
//
//        when(expenseRepository.findByUserId(1, pageable)).thenReturn(page);
//
//        Page<Expense> result = expenseService.findPaginatedByUser(1, 5, "id", "asc", 1);
//
//        assertThat(result).isNotNull();
//        verify(expenseRepository).findByUserId(eq(1), any(PageRequest.class));
//    }
