package com.example.SmartSpendExpense.controller;

import com.example.SmartSpendExpense.model.Users;
import com.example.SmartSpendExpense.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserService userService;
    @GetMapping
    public String index(){
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/user")
    public String user(Model model){
        Users obj=new Users();
        model.addAttribute("user",obj);
        return "user";
    }
    @PostMapping("/saveuser")
    public String createuser(@ModelAttribute("user") Users user){
        userService.saveuser(user);
        return "redirect:/login";
    }
}
