package com.rakbank.expensems.repository;

import com.rakbank.expensems.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findById(String id);


}
