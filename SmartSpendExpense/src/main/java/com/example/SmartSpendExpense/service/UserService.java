package com.example.SmartSpendExpense.service;

import com.example.SmartSpendExpense.model.Users;
import com.example.SmartSpendExpense.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();


    public Users saveuser(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }
}
