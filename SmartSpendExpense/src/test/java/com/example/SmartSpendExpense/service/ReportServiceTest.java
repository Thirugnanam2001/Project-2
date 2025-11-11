package com.example.SmartSpendExpense.service;

import com.example.SmartSpendExpense.model.Expense;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReportServiceTest {

    private final ReportService reportService = new ReportService();

    @Test
    void testWritePdf() throws Exception {
        Expense expense = new Expense();
        expense.setTitle("Test");
        expense.setAmount(BigDecimal.TEN);
        expense.setDate(LocalDate.now());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        reportService.writePdf(List.of(expense), out);

        assertThat(out.toByteArray()).isNotEmpty();
    }
}