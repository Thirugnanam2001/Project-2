package com.example.SmartSpendExpense.controller;

import com.example.SmartSpendExpense.model.Users;
import com.example.SmartSpendExpense.repository.ExpenseRepository;
import com.example.SmartSpendExpense.repository.UserRepository;
import com.example.SmartSpendExpense.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChartController.class)
@Import(ChartControllerTest.TestConfig.class)
class ChartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Inject the mocks that Spring created from TestConfig
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseService expenseService;

    static class TestConfig {
        @Bean ExpenseService expenseService() { return Mockito.mock(ExpenseService.class); }
        @Bean ExpenseRepository expenseRepository() { return Mockito.mock(ExpenseRepository.class); }
        @Bean UserRepository userRepository() { return Mockito.mock(UserRepository.class); }
    }

    @Test
    @WithMockUser(username = "john")
    void testIndexLoadsHome() throws Exception {
        Users user = new Users();
        user.setId(1);
        user.setUsername("john");

        // Use the injected mocks, not TestConfig.userRepository()
        Mockito.when(userRepository.findByUsername("john")).thenReturn(user);
        Mockito.when(expenseRepository.findByUserId(1)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/expense/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("data"));
    }
}