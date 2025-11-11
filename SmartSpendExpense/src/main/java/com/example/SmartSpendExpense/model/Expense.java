package com.example.SmartSpendExpense.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name= "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @Column
    private BigDecimal amount;

    @Column
    private String category;

    @Column
    private String type;

    @Column(name = "expense_date")
    private LocalDate date;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;


    // Constructors
    public Expense() {}

    public Expense(String title, BigDecimal amount, String category, String type, LocalDate date, String description, Users user) {
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.type = type;
        this.date = date;
        this.description = description;
        this.user = user;
    }


    // Getters and Setters

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public BigDecimal getAmount() { return amount; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Users getUser() { return user; }

    public void setUser(Users user) { this.user = user; }
}
