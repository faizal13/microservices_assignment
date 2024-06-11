package com.rakbank.notificationms.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Notification extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="budget_amt")
    private BigDecimal budgetAmt;

    @Column(name="expense_amt")
    private BigDecimal expenseAmt;

    @Column(name="budget_category")
    private String budgetCategory;

    @Column(name="expense_desc")
    private String expenseDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBudgetAmt() {
        return budgetAmt;
    }

    public void setBudgetAmt(BigDecimal budgetAmt) {
        this.budgetAmt = budgetAmt;
    }

    public BigDecimal getExpenseAmt() {
        return expenseAmt;
    }

    public void setExpenseAmt(BigDecimal expenseAmt) {
        this.expenseAmt = expenseAmt;
    }

    public String getBudgetCategory() {
        return budgetCategory;
    }

    public void setBudgetCategory(String budgetCategory) {
        this.budgetCategory = budgetCategory;
    }

    public String getExpenseDesc() {
        return expenseDesc;
    }

    public void setExpenseDesc(String expenseDesc) {
        this.expenseDesc = expenseDesc;
    }
}
