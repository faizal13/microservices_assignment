package com.rakbank.notificationms.model;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public class NotificationRequest {


    @NotNull
    private String budgetCategory;

    @Positive
    @Digits(integer = Integer.MAX_VALUE , fraction = 2)
    private double budgetAmount;

    @Positive
    @Digits(integer = Integer.MAX_VALUE , fraction = 2)
    private double expenseAmount;

    @NotNull
    private String expenseDesc;

    public String getBudgetCategory() {
        return budgetCategory;
    }

    public void setBudgetCategory(String budgetCategory) {
        this.budgetCategory = budgetCategory;
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseDesc() {
        return expenseDesc;
    }

    public void setExpenseDesc(String expenseDesc) {
        this.expenseDesc = expenseDesc;
    }
}
