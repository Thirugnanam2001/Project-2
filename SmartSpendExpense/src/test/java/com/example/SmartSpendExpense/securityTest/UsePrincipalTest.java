package com.example.SmartSpendExpense.securityTest;

import com.example.SmartSpendExpense.config.UsePrincipal;
import com.example.SmartSpendExpense.model.Users;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UsePrincipalTest {

    @Test
    void testUserDetailsMapping() {
        Users user = new Users();
        user.setUsername("sama");
        user.setPassword("20012111");
        user.setEmail("sam@example.com");

        UsePrincipal principal = new UsePrincipal(user);

        assertThat(principal.getUsername()).isEqualTo("sama");
        assertThat(principal.getPassword()).isEqualTo("20012111");
        assertThat(principal.getAuthorities()).hasSize(1);
    }
}