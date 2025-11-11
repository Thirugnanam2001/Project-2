package com.example.SmartSpendExpense.securityTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;


//    @Test
//    void testBeansLoaded() {
//       assertThat(authenticationProvider).isNotNull();
//        assertThat(passwordEncoder.encode("test")).isNotBlank();
//    }
@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testPasswordEncoderWorks() {
        String encoded = passwordEncoder.encode("secret");
        assertThat(passwordEncoder.matches("secret", encoded)).isTrue();
    }

}