package com.example.SmartSpendExpense.controller;

import com.example.SmartSpendExpense.model.Expense;
import com.example.SmartSpendExpense.model.Users;
import com.example.SmartSpendExpense.repository.UserRepository;
import com.example.SmartSpendExpense.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;
    @Autowired
    UserRepository userRepository;
    @GetMapping("/add")
    public String add(Model model){
        Expense obj=new Expense();
        model.addAttribute("expense",obj);
        return "add";
    }

    @GetMapping("/display")
    public String getAllExpense(Model model, String keyword) {
        return findpaginated(1, "id", "asc", model, keyword);
    }

    @GetMapping("/page/{pageNo}")
    public String findpaginated(@PathVariable("pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model,
                                String keyword) {

        // 🔐 Get logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = userRepository.findByUsername(username);

        int pageSize = 5;

        // 🧠 Paginated result for this user
        Page<Expense> page;

        if (keyword != null && !keyword.isBlank()) {
            page = expenseService.findPaginatedByKeywordAndUser(pageNo, pageSize, sortField, sortDir, keyword, user.getId());
        } else {
            page = expenseService.findPaginatedByUser(pageNo, pageSize, sortField, sortDir, user.getId());
        }

        List<Expense> listExpense = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("listExpense", listExpense);

        return "display";
    }

    @PostMapping("/save")
    public String saveExpense(@ModelAttribute("expense") Expense expense) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        expense.setUser(user);

        expenseService.save(expense);
        return "redirect:/expense/display";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        expenseService.del(id);
        return "redirect:/expense/display";

    }
    @GetMapping("/update/{id}")
    public String update(@PathVariable Integer id,Model model){
        Optional<Expense> expense=expenseService.findById(id);
        model.addAttribute("expense",expense);
        return "update";
    }

}
