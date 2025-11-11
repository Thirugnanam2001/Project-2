package com.example.SmartSpendExpense.repository;

import com.example.SmartSpendExpense.model.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUsername() {
        // Given
        Users user = new Users();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");

        // Use TestEntityManager to persist
        Users savedUser = entityManager.persistAndFlush(user);

        // When
        Users found = userRepository.findByUsername("testuser");

        // Then
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo("testuser");
        assertThat(found.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void testFindByEmail() {
        // Given
        Users user = new Users();
        user.setUsername("testuser2");
        user.setEmail("test2@example.com");
        user.setPassword("password");

        entityManager.persistAndFlush(user);

        // When
        Users found = userRepository.findByEmail("test2@example.com");

        // Then
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("test2@example.com");
    }
}










//import com.example.SmartSpendExpense.model.Users;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.TestPropertySource;
//
//import static org.assertj.core.api.Assertions.assertThat;

//@DataJpaTest
//@TestPropertySource(locations = "classpath:application-test.properties")
//class UserRepositoryTest {
//
//    @Autowired
//    private UserRepository userRepository;

//    @Autowired
//    private TestEntityManager entityManager;
//
//    private Users testUser;
//

//    @BeforeEach
//    void setUp() {
//        // Create and persist test user
//        testUser = new Users();
//        testUser.setUsername("testuser");
//        testUser.setEmail("test@example.com");
//        testUser.setPassword("password");
//        testUser = entityManager.persist(testUser);
//        entityManager.flush();
//    }
//
//    @Test
//    void testFindByUsername() {
//        // When
//        Users found = userRepository.findByUsername("testuser");
//
//        // Then
//        assertThat(found.getUsername()).isEqualTo("testuser");
//        assertThat(found).isNotNull();
//        assertThat(found.getEmail()).isEqualTo("test@example.com");
//    }
//    @Test
//    void testFindByUsername(){
//        Users user = new Users();
//        user.setUsername("Thiru");
//        user.setPassword("password");
//        user.setEmail("alice@example.com");
//        userRepository.save(user);
//
//        Users found = userRepository.findByUsername("Thiru");
//        assertThat(found).isNotNull();
//        assertThat(found.getEmail()).isEqualTo("alice@example.com");
//    }
//}

