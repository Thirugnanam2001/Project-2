package com.example.SmartSpendExpense.repository;

import com.example.SmartSpendExpense.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Integer> {
  @Query(value = "select * from expenses e where e.type like %:keyword% or e.category like %:keyword% or e.title like %:keyword% ",nativeQuery = true )
  List<Expense> findByKeyword(@Param("keyword") String keyword);

  List<Expense> findByUserId(Integer userId);
  Page<Expense> findByUserId(Integer userId, Pageable pageable);
  @Query("SELECT e FROM Expense e WHERE e.user.id = :userId AND " +
          "(LOWER(e.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
          "LOWER(e.category) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
          "LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
  Page<Expense> searchByKeywordAndUserId(@Param("keyword") String keyword,
                                         @Param("userId") Integer userId,
                                         Pageable pageable);


}
