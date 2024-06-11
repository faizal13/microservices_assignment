package com.rakbank.budgetms.entity;

import jakarta.persistence.*;

@Embeddable
public class Category {

    @Column(name = "cat_name", unique=true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
