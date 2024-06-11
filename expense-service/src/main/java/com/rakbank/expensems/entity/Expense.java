package com.rakbank.expensems.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Expense extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="expense_amt")
    private BigDecimal expenseAmt;

    @Column(name="category")
    private String category;

    @Column(name="expense_desc")
    private String expenseDesc;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public BigDecimal getExpenseAmt() {
        return expenseAmt;
    }

    public void setExpenseAmt(BigDecimal expenseAmt) {
        this.expenseAmt = expenseAmt;
    }

    public String getExpenseDesc() {
        return expenseDesc;
    }

    public void setExpenseDesc(String expenseDesc) {
        this.expenseDesc = expenseDesc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
