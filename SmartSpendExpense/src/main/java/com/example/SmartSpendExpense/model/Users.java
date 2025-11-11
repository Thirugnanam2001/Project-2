package com.example.SmartSpendExpense.model;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses;

    public Users() {}

    public Users(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
