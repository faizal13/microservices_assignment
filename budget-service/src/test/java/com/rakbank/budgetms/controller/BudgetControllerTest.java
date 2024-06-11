package com.rakbank.budgetms.controller;

import com.rakbank.budgetms.exception.BudgetServiceException;
import com.rakbank.budgetms.model.BudgetRequest;
import com.rakbank.budgetms.model.BudgetResponse;
import com.rakbank.budgetms.service.BudgetService;
import com.rakbank.budgetms.utils.Constant;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BudgetControllerTest {

    @Mock
    private BudgetService budgetService;

    @InjectMocks
    private BudgetController budgetController;

    private BudgetRequest validRequest;

    @Mock
    HttpServletRequest request;

    @BeforeEach
    void setUp() {
        validRequest = new BudgetRequest();
    }

    @Test
    void addBudget_Success() {
        when(budgetService.addBudget(validRequest)).thenReturn(new BudgetResponse());
        ResponseEntity<?> response = budgetController.addBudget(validRequest,request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void addBudgetException() {
        doThrow(new BudgetServiceException("Category already present")).when(budgetService).addBudget(validRequest);
        assertThrows(BudgetServiceException.class, () -> budgetController.addBudget(validRequest, request));
    }

    @Test
    void getAllBudgetSuccess() {
        when(budgetService.getAllBudgets()).thenReturn(new ArrayList<>());
        ResponseEntity<?> response = budgetController.getAllBudgets(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getBudgetSuccess() {
        when(budgetService.getBudget(anyString())).thenReturn(new BudgetResponse("12345","Grocery", 8657.00));
        ResponseEntity<?> response = budgetController.getBudget("12345", request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getBudgetException() {
        when(budgetService.getBudget(anyString())).thenThrow(new BudgetServiceException(Constant.NO_RECORDS));
        assertThrows(BudgetServiceException.class, () -> budgetController.getBudget("12345", request));
    }

    @Test
    void deleteBudgetSuccess() {
        doNothing().when(budgetService).removeBudget(anyString());
        ResponseEntity<?> response = budgetController.deleteBudget("12345", request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

}
