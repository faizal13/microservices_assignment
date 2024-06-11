package com.rakbank.budgetms.mapper;

import com.rakbank.budgetms.entity.Budget;
import com.rakbank.budgetms.model.BudgetRequest;
import com.rakbank.budgetms.model.BudgetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BudgetMapper {

    @Mapping(target = "category.name", source = "category")
    Budget toEntity(BudgetRequest budgetRequest);

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "budgetId", source = "id")
    BudgetResponse toDomain(Budget budget);

}
