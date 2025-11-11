package com.example.SmartSpendExpense.securityTest;

import com.example.SmartSpendExpense.config.MyUser;
import com.example.SmartSpendExpense.model.Users;
import com.example.SmartSpendExpense.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MyUserTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final MyUser myUser = new MyUser();

    @Test
    void testLoadUserByUsername_Success() {
        Users user = new Users();
        user.setUsername("bob");
        user.setPassword("pass");
        user.setEmail("bob@example.com");

        Mockito.when(userRepository.findByUsername("bob")).thenReturn(user);

        // Inject mock
        myUser.userRepository = userRepository;

        UserDetails details = myUser.loadUserByUsername("bob");
        assertThat(details.getUsername()).isEqualTo("bob");
    }

    @Test
    void testLoadUserByUsername_NotFound() {
        Mockito.when(userRepository.findByUsername("ghost")).thenReturn(null);
        myUser.userRepository = userRepository;

        assertThrows(RuntimeException.class, () -> myUser.loadUserByUsername("ghost"));
    }
}