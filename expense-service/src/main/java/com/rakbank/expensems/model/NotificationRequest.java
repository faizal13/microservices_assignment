package com.rakbank.expensems.model;

public class NotificationRequest {

    

    private String budgetCategory;

    private double budgetAmount;

    private double expenseAmount;

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

    @Override
    public String toString() {
        return "NotificationRequest{" +
                "budgetCategory='" + budgetCategory + '\'' +
                ", budgetAmount=" + budgetAmount +
                ", expenseAmount=" + expenseAmount +
                ", expenseDesc='" + expenseDesc + '\'' +
                '}';
    }
}
