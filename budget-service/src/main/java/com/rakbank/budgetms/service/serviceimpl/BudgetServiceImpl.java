package com.rakbank.budgetms.service.serviceimpl;


import com.rakbank.budgetms.entity.Budget;
import com.rakbank.budgetms.exception.BudgetServiceException;
import com.rakbank.budgetms.exception.RecordNotFoundException;
import com.rakbank.budgetms.mapper.BudgetMapper;
import com.rakbank.budgetms.model.BudgetRequest;
import com.rakbank.budgetms.model.BudgetResponse;
import com.rakbank.budgetms.repository.BudgetRepository;
import com.rakbank.budgetms.service.BudgetService;
import com.rakbank.budgetms.utils.Constant;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    private final BudgetMapper budgetMapper;


    @Autowired
    public BudgetServiceImpl(BudgetRepository budgetRepository,
                             BudgetMapper budgetMapper) {
        this.budgetRepository = budgetRepository;
        this.budgetMapper = budgetMapper;
    }

    @Transactional
    public BudgetResponse addBudget(BudgetRequest budgetRequest) {
        if(isCategoryDuplicate(budgetRequest))
            throw new BudgetServiceException("Category already present");

      return  Optional.of(budgetRepository
                .save(budgetMapper.toEntity(budgetRequest)))
                .map(budgetMapper::toDomain).get();
    }

    private boolean isCategoryDuplicate(BudgetRequest budgetRequest) {
        return budgetRepository.findByCategory(budgetRequest.getCategory()).isPresent();

    }

    @Transactional
    public BudgetResponse updateBudget(String budgetId, BudgetRequest budgetRequest) {

        Optional<Budget> budgetData = budgetRepository.findByCategory(budgetRequest.getCategory());
        if(budgetData.isPresent() && !budgetData.get().getId().equals(budgetId))
            throw new BudgetServiceException("Category already present");

        return Optional.of(budgetRepository.save(budgetRepository.findById(budgetId)
                .map(budget -> {
                    budget.setAmount(new BigDecimal(budgetRequest.getAmount()));
                    budget.getCategory().setName(budgetRequest.getCategory());
                    return budget;
                })
                .orElseThrow(() -> new RecordNotFoundException(Constant.NO_RECORDS))))
                .map(budgetMapper::toDomain).get();

    }

    public List<Budget> getAllBudgets() {
        return  budgetRepository.findAll();
    }

    public BudgetResponse getBudget(String categoryName) {
       return budgetRepository.findByCategory(categoryName)
               .map(budgetMapper::toDomain)
               .orElse(null);
    }

    @Override
    public void removeBudget(String id) {
        budgetRepository.delete(budgetRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(Constant.NO_RECORDS)));
    }
}
