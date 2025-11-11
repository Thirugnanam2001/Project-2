package com.example.SmartSpendExpense.controller;

import com.example.SmartSpendExpense.model.Expense;
import com.example.SmartSpendExpense.model.Users;
import com.example.SmartSpendExpense.repository.ExpenseRepository;
import com.example.SmartSpendExpense.repository.UserRepository;
import com.example.SmartSpendExpense.service.ReportService;
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




@WebMvcTest(ReportController.class)
@Import(ReportControllerTest.TestConfig.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Inject the mocks that Spring created from TestConfig
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportService reportService;

    static class TestConfig {
        @Bean
        ExpenseRepository expenseRepository() { return Mockito.mock(ExpenseRepository.class); }
        @Bean UserRepository userRepository() { return Mockito.mock(UserRepository.class); }
        @Bean ReportService reportService() { return Mockito.mock(ReportService.class); }
    }

    @Test
    @WithMockUser(username = "bob")
    void testExportPdf() throws Exception {
        Users user = new Users();
        user.setId(1);
        user.setUsername("bob");

        // Use the injected mocks, not TestConfig.userRepository()
        Mockito.when(userRepository.findByUsername("bob")).thenReturn(user);
        Mockito.when(expenseRepository.findByUserId(1)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/expense/report/pdf"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=expenses.pdf"));
    }

    @Test
    @WithMockUser(username = "bob")
    void testExportExcel() throws Exception {
        Users user = new Users();
        user.setId(1);
        user.setUsername("bob");

        Mockito.when(userRepository.findByUsername("bob")).thenReturn(user);
        Mockito.when(expenseRepository.findByUserId(1)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/expense/report/excel"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=expenses.xlsx"));
    }
}