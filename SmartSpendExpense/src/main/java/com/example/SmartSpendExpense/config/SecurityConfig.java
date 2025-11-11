package com.example.SmartSpendExpense.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    UserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/expense/index", true)
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());
        return http.build();
    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(cus->cus.disable());
//        http.authorizeHttpRequests(auth ->
//                auth
//                        .requestMatchers("/auth/**").permitAll()
//                        .anyRequest().authenticated());
//        http.formLogin(form->form.loginPage("/auth/login")
//                .defaultSuccessUrl("/expense/index",true)
//                .permitAll());
//        http.formLogin(Customizer.withDefaults());
//        return http.build();
//
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(new BCryptPasswordEncoder());
//        return provider;
//    }

}
