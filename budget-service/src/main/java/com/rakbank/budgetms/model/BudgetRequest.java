package com.rakbank.budgetms.model;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

public class BudgetRequest {

    @NotNull
    @NotEmpty
    private String category;

    @Positive
    @Digits(integer = Integer.MAX_VALUE , fraction = 2)
    private double amount;

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

}
