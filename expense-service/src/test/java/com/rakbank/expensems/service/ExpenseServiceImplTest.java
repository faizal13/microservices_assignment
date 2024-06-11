package com.rakbank.expensems.service;

import com.rakbank.expensems.client.BudgetServiceWebClient;
import com.rakbank.expensems.client.NotificationServiceWebClient;
import com.rakbank.expensems.entity.Expense;
import com.rakbank.expensems.exception.ExpenseServiceException;
import com.rakbank.expensems.mapper.ExpenseMapper;
import com.rakbank.expensems.model.*;
import com.rakbank.expensems.repository.ExpenseRepository;
import com.rakbank.expensems.service.serviceImpl.ExpenseServiceImpl;
import com.rakbank.expensems.utils.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private ExpenseMapper expenseMapper;

    @Mock
    private BudgetServiceWebClient budgetClient;

    @Mock
    private NotificationServiceWebClient notificationClient;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    private ExpenseRequest expenseRequest;
    private Expense expense;
    private BudgetResponse budgetResponse;
    private NotificationResponse notificationResponse;
    private ExpenseResponse expenseResponse;

    @BeforeEach
    void setUp() {
        expenseRequest = new ExpenseRequest();
        expenseRequest.setCategoryName("Food");
        expenseRequest.setExpenseAmount(Double.valueOf(100));
        expenseRequest.setDescription("Dinner");

        expense = new Expense();
        expense.setExpenseAmt(BigDecimal.valueOf(100));

        budgetResponse = new BudgetResponse();
        budgetResponse.setCategoryName("Food");
        budgetResponse.setAmount(Double.valueOf(50));

        notificationResponse = new NotificationResponse();
        notificationResponse.setNotificationId(Long.valueOf(12345));

        expenseResponse = new ExpenseResponse();
        expenseResponse.setDescription("Expense Added");
    }

    @Test
    void testAddExpense() {
        when(expenseMapper.toEntity(any(ExpenseRequest.class))).thenReturn(expense);
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);
        when(expenseMapper.toDomain(any(Expense.class))).thenReturn(expenseResponse);
        when(budgetClient.getBudgetCategory(eq("Food"))).thenReturn(Mono.just(budgetResponse));
        when(notificationClient.createNotification(any(NotificationRequest.class))).thenReturn(Mono.just(notificationResponse));

        ExpenseResponse response = expenseService.addExpense(expenseRequest);

        assertNotNull(response);
        assertEquals("Expense amount exceeded. User has been notified with notificationId 12345", response.getDescription());

        verify(expenseRepository).save(any(Expense.class));
        verify(budgetClient).getBudgetCategory(eq("Food"));
        verify(notificationClient).createNotification(any(NotificationRequest.class));
    }

    @Test
    void testUpdateExpense() {
        String expenseId = "1";

        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(expense));
        when(expenseMapper.toEntity(any(ExpenseRequest.class))).thenReturn(expense);
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);
        when(expenseMapper.toDomain(any(Expense.class))).thenReturn(expenseResponse);
        when(budgetClient.getBudgetCategory(eq("Food"))).thenReturn(Mono.just(budgetResponse));
        when(notificationClient.createNotification(any(NotificationRequest.class))).thenReturn(Mono.just(notificationResponse));

        ExpenseResponse response = expenseService.updateExpense(expenseId, expenseRequest);

        assertNotNull(response);
        assertEquals("Expense amount exceeded. User has been notified with notificationId 12345", response.getDescription());

        verify(expenseRepository).findById(expenseId);
        verify(expenseRepository).save(any(Expense.class));
        verify(budgetClient).getBudgetCategory(eq("Food"));
        verify(notificationClient).createNotification(any(NotificationRequest.class));
    }

    @Test
    void testGetAllExpenses() {
        List<Expense> expenses = Stream.of(expense).collect(Collectors.toList());
        List<ExpenseResponse> expenseResponses = Stream.of(expenseResponse).collect(Collectors.toList());

        when(expenseRepository.findAll()).thenReturn(expenses);
        when(expenseMapper.toDomain(any(Expense.class))).thenReturn(expenseResponse);

        List<ExpenseResponse> responses = expenseService.getAllExpenses();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(expenseResponse.getDescription(), responses.get(0).getDescription());

        verify(expenseRepository).findAll();
        verify(expenseMapper, times(expenses.size())).toDomain(any(Expense.class));
    }

    @Test
    void testRemoveExpense() {
        String expenseId = "1";

        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(expense));

        assertDoesNotThrow(() -> expenseService.removeExpense(expenseId));

        verify(expenseRepository).findById(expenseId);
        verify(expenseRepository).delete(expense);
    }

    @Test
    void testRemoveExpenseNotFound() {
        String expenseId = "1";

        when(expenseRepository.findById(expenseId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ExpenseServiceException.class, () -> expenseService.removeExpense(expenseId));

        assertEquals(Constant.NO_RECORDS, exception.getMessage());

        verify(expenseRepository).findById(expenseId);
        verify(expenseRepository, never()).delete(any(Expense.class));
    }

    @Test
    void testGetExpense() {
        String expenseId = "1";

        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(expense));
        when(expenseMapper.toDomain(expense)).thenReturn(expenseResponse);

        ExpenseResponse response = expenseService.getExpense(expenseId);

        assertNotNull(response);
        assertEquals(expenseResponse.getDescription(), response.getDescription());

        verify(expenseRepository).findById(expenseId);
        verify(expenseMapper).toDomain(expense);
    }

    @Test
    void testGetExpenseNotFound() {
        String expenseId = "1";

        when(expenseRepository.findById(expenseId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ExpenseServiceException.class, () -> expenseService.getExpense(expenseId));

        assertEquals(Constant.NO_RECORDS, exception.getMessage());

        verify(expenseRepository).findById(expenseId);
        verify(expenseMapper, never()).toDomain(any(Expense.class));
    }



}

