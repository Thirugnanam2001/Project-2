package com.example.SmartSpendExpense.controller;


import com.example.SmartSpendExpense.model.Expense;
import com.example.SmartSpendExpense.model.Users;
import com.example.SmartSpendExpense.repository.ExpenseRepository;
import com.example.SmartSpendExpense.repository.UserRepository;
import com.example.SmartSpendExpense.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class ReportController {

    private final ExpenseRepository expenseRepository;
    private  final UserRepository userRepository;
    private final ReportService reportService;

    @GetMapping("/expense/report/pdf")
    public void exportPdf(Authentication auth, HttpServletResponse response) throws Exception {
        Users user = userRepository.findByUsername(auth.getName());
        List<Expense> expenses = expenseRepository.findByUserId(user.getId());
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=expenses.pdf");
        reportService.writePdf(expenses, response.getOutputStream());
    }
    @GetMapping("/expense/report/excel")
    public void exportExcel(Authentication auth, HttpServletResponse response) throws Exception {
        // Get logged-in user
        String username = auth.getName();
        Users user = userRepository.findByUsername(username); // <-- correct method

        if (user == null) {
            throw new RuntimeException("User not found");
        }
        // Fetch this user's expenses
        List<Expense> expenses = expenseRepository.findByUserId(user.getId());

        // Set response headers
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=expenses.xlsx");

        // Write Excel file
        reportService.writeExcel(expenses, response.getOutputStream());
    }

}

