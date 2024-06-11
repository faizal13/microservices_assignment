package com.rakbank.budgetms.repository;

import com.rakbank.budgetms.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findById(String id);


    @Query(value = "SELECT * FROM BUDGET b WHERE b.cat_name = :category",
            nativeQuery = true)
    Optional<Budget> findByCategory(@Param("category") String category);


}
