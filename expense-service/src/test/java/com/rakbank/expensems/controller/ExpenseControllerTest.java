package com.rakbank.expensems.controller;

import com.rakbank.expensems.exception.RecordNotFoundException;
import com.rakbank.expensems.model.CustomResponse;
import com.rakbank.expensems.model.ExpenseRequest;
import com.rakbank.expensems.model.ExpenseResponse;
import com.rakbank.expensems.service.ExpenseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseControllerTest {

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private ExpenseController expenseController;

    @Test
    void testAddExpense_Success() {
        ExpenseRequest expenseRequest = new ExpenseRequest();
        HttpServletRequest request = mock(HttpServletRequest.class);
        ExpenseResponse expenseResponse = new ExpenseResponse();
        when(expenseService.addExpense(expenseRequest)).thenReturn(expenseResponse);

        ResponseEntity<CustomResponse<ExpenseResponse>> responseEntity = expenseController.addExpense(expenseRequest, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(expenseService, times(1)).addExpense(expenseRequest);
        verify(request, times(1)).getServletPath();
    }


    @Test
    void testAddExpense_ExceptionThrown() {
        ExpenseRequest expenseRequest = new ExpenseRequest();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(expenseService.addExpense(expenseRequest)).thenThrow(new RuntimeException("Internal server error"));

        assertThrows(RuntimeException.class, () -> expenseController.addExpense(expenseRequest, request));

        verify(expenseService, times(1)).addExpense(expenseRequest);
        verify(request, never()).getServletPath();
    }

    @Test
    void testUpdateExpense_Success() {
        String expenseId = "123";
        ExpenseRequest expenseRequest = new ExpenseRequest();
        HttpServletRequest request = mock(HttpServletRequest.class);
        ExpenseResponse expenseResponse = new ExpenseResponse();
        when(expenseService.updateExpense(expenseId, expenseRequest)).thenReturn(expenseResponse);

        ResponseEntity<CustomResponse<ExpenseResponse>> responseEntity = expenseController.updateExpense(expenseId, expenseRequest, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(expenseService, times(1)).updateExpense(expenseId, expenseRequest);
        verify(request, times(1)).getServletPath();
    }


    @Test
    void testUpdateExpense_ExceptionThrown() {
        // Mocking
        String expenseId = "123";
        ExpenseRequest expenseRequest = new ExpenseRequest();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(expenseService.updateExpense(expenseId, expenseRequest)).thenThrow(new RuntimeException("Internal server error"));

        // Test
        assertThrows(RuntimeException.class, () -> expenseController.updateExpense(expenseId, expenseRequest, request));

        // Verify
        verify(expenseService, times(1)).updateExpense(expenseId, expenseRequest);
        verify(request, never()).getServletPath();
    }

    @Test
    void testGetAllExpenses_Success() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        List<ExpenseResponse> expenseResponses = Collections.singletonList(new ExpenseResponse());
        when(expenseService.getAllExpenses()).thenReturn(expenseResponses);

        ResponseEntity<CustomResponse<List<ExpenseResponse>>> responseEntity = expenseController.getAllExpenses(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(expenseService, times(1)).getAllExpenses();
        verify(request, times(1)).getServletPath();
    }

    @Test
    void testGetAllExpenses_EmptyList() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(expenseService.getAllExpenses()).thenReturn(Collections.emptyList());

        ResponseEntity<CustomResponse<List<ExpenseResponse>>> responseEntity = expenseController.getAllExpenses(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().getData().isEmpty());
        verify(expenseService, times(1)).getAllExpenses();
        verify(request, times(1)).getServletPath();
    }

    @Test
    void testGetExpense_Success() {
        String expenseId = "123";
        HttpServletRequest request = mock(HttpServletRequest.class);
        ExpenseResponse expenseResponse = new ExpenseResponse();
        when(expenseService.getExpense(expenseId)).thenReturn(expenseResponse);

        ResponseEntity<CustomResponse<ExpenseResponse>> responseEntity = expenseController.getExpense(expenseId, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(expenseService, times(1)).getExpense(expenseId);
        verify(request, times(1)).getServletPath();
    }


    @Test
    void testDeleteExpense_Success() {
        String expenseId = "123";
        HttpServletRequest request = mock(HttpServletRequest.class);

        ResponseEntity<CustomResponse<String>> responseEntity = expenseController.deleteExpense(expenseId, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(expenseService, times(1)).removeExpense(expenseId);
        verify(request, times(1)).getServletPath();
    }
}
