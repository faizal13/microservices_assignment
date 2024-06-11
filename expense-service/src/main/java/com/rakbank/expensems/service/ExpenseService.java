package com.rakbank.expensems.service;


import com.rakbank.expensems.model.ExpenseRequest;
import com.rakbank.expensems.model.ExpenseResponse;
import com.rakbank.expensems.model.NotificationResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ExpenseService {

    ExpenseResponse addExpense(ExpenseRequest expenseRequest);

    ExpenseResponse updateExpense(String expenseId, ExpenseRequest budgetRequest);

    List<ExpenseResponse> getAllExpenses();

    void removeExpense(String id);

    ExpenseResponse getExpense(String id);
}
