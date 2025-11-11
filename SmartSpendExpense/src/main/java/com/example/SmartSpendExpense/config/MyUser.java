package com.example.SmartSpendExpense.config;

import com.example.SmartSpendExpense.model.Users;
import com.example.SmartSpendExpense.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUser implements UserDetailsService {
    @Autowired
    public UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user= userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("User not found");
        }
        return new UsePrincipal(user);
    }
}
