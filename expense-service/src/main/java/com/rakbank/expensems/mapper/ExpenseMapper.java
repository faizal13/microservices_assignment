package com.rakbank.expensems.mapper;

import com.rakbank.expensems.entity.Expense;
import com.rakbank.expensems.model.ExpenseRequest;
import com.rakbank.expensems.model.ExpenseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    @Mapping(target = "expenseAmt", source = "expenseAmount")
    @Mapping(target = "category", source = "categoryName")
    @Mapping(target = "expenseDesc", source = "description")
    Expense toEntity(ExpenseRequest expenseRequest);

    @Mapping(target = "expenseAmount", source = "expenseAmt")
    @Mapping(target = "categoryName", source = "category")
    @Mapping(target = "description", source = "expenseDesc")
    ExpenseResponse toDomain(Expense expense);


}
