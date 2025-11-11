package com.example.SmartSpendExpense.service;

import com.example.SmartSpendExpense.model.Users;
import com.example.SmartSpendExpense.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService; // your service class

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {
        Users user = new Users();
        user.setUsername("john");
        user.setPassword("pwd");
        user.setEmail("john@example.com");

        when(userRepository.save(user)).thenReturn(user);

        Users saved = userService.saveuser(user);

        verify(userRepository).save(user);
        assertThat(saved.getUsername()).isEqualTo("john");
    }
}