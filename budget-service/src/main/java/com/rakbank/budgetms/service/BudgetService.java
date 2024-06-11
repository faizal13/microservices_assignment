package com.rakbank.budgetms.service;

import com.rakbank.budgetms.entity.Budget;
import com.rakbank.budgetms.model.BudgetRequest;
import com.rakbank.budgetms.model.BudgetResponse;

import java.util.List;

public interface BudgetService {

    BudgetResponse addBudget(BudgetRequest budgetRequest);

    BudgetResponse updateBudget(String budgetId, BudgetRequest budgetRequest);

    List<Budget> getAllBudgets();

    BudgetResponse getBudget(String categoryName);

    void removeBudget(String id);
}
