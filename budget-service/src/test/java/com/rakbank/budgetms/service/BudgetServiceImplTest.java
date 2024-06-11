package com.rakbank.budgetms.service;

import com.rakbank.budgetms.entity.Budget;
import com.rakbank.budgetms.exception.BudgetServiceException;
import com.rakbank.budgetms.exception.RecordNotFoundException;
import com.rakbank.budgetms.mapper.BudgetMapper;
import com.rakbank.budgetms.model.BudgetRequest;
import com.rakbank.budgetms.model.BudgetResponse;
import com.rakbank.budgetms.repository.BudgetRepository;
import com.rakbank.budgetms.service.serviceimpl.BudgetServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BudgetServiceImplTest {

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private BudgetMapper budgetMapper;

    @InjectMocks
    private BudgetServiceImpl budgetService;

    @Test
    void testAddBudget_Success() {
        BudgetRequest budgetRequest = new BudgetRequest();
        Budget budget = new Budget();
        when(budgetMapper.toEntity(any())).thenReturn(budget);
        when(budgetRepository.findByCategory(any())).thenReturn(Optional.empty());
        when(budgetRepository.save(any())).thenReturn(budget);
        when(budgetMapper.toDomain(any())).thenReturn(new BudgetResponse());

        BudgetResponse response = budgetService.addBudget(budgetRequest);

        assertNotNull(response);
        verify(budgetRepository, times(1)).findByCategory(any());
        verify(budgetRepository, times(1)).save(any());
    }

    @Test
    void testAddBudget_CategoryDuplicate() {
        BudgetRequest budgetRequest = new BudgetRequest();
        when(budgetRepository.findByCategory(any())).thenReturn(Optional.of(new Budget()));

        assertThrows(BudgetServiceException.class, () -> budgetService.addBudget(budgetRequest));
        verify(budgetRepository, times(1)).findByCategory(any());
        verify(budgetRepository, never()).save(any());
    }


    @Test
    void testUpdateBudget_RecordNotFound() {
        String budgetId = "1";
        BudgetRequest budgetRequest = new BudgetRequest();
        when(budgetRepository.findById(budgetId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> budgetService.updateBudget(budgetId, budgetRequest));
        verify(budgetRepository, times(1)).findById(budgetId);
        verify(budgetRepository, never()).save(any());
    }

    @Test
    void testGetAllBudgets_Success() {
        List<Budget> budgets = Collections.singletonList(new Budget());
        when(budgetRepository.findAll()).thenReturn(budgets);

        List<Budget> result = budgetService.getAllBudgets();

        assertFalse(result.isEmpty());
        assertEquals(budgets.size(), result.size());
        verify(budgetRepository, times(1)).findAll();
    }

    @Test
    void testGetBudget_Success() {
        String categoryName = "Food";
        Budget budget = new Budget();
        when(budgetRepository.findByCategory(categoryName)).thenReturn(Optional.of(budget));
        when(budgetMapper.toDomain(budget)).thenReturn(new BudgetResponse());

        BudgetResponse response = budgetService.getBudget(categoryName);

        assertNotNull(response);
        verify(budgetRepository, times(1)).findByCategory(categoryName);
    }

    @Test
    void testGetBudget_NotFound() {
        String categoryName = "NonExistingCategory";
        when(budgetRepository.findByCategory(categoryName)).thenReturn(Optional.empty());

        BudgetResponse response = budgetService.getBudget(categoryName);

        assertNull(response);
        verify(budgetRepository, times(1)).findByCategory(categoryName);
    }

    @Test
    void testRemoveBudget_Success() {
        String id = "1";
        when(budgetRepository.findById(id)).thenReturn(Optional.of(new Budget()));

        budgetService.removeBudget(id);

        verify(budgetRepository, times(1)).findById(id);
        verify(budgetRepository, times(1)).delete(any());
    }

    @Test
    void testRemoveBudget_RecordNotFound() {
        String id = "NonExistingId";
        when(budgetRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> budgetService.removeBudget(id));
        verify(budgetRepository, times(1)).findById(id);
        verify(budgetRepository, never()).delete(any());
    }
}

