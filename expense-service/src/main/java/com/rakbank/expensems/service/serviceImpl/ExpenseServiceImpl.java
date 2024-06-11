package com.rakbank.expensems.service.serviceImpl;


import com.rakbank.expensems.client.BudgetServiceWebClient;
import com.rakbank.expensems.client.NotificationServiceWebClient;
import com.rakbank.expensems.entity.Expense;
import com.rakbank.expensems.exception.ExpenseServiceException;
import com.rakbank.expensems.exception.RecordNotFoundException;
import com.rakbank.expensems.mapper.ExpenseMapper;
import com.rakbank.expensems.model.*;
import com.rakbank.expensems.repository.ExpenseRepository;
import com.rakbank.expensems.service.ExpenseService;
import com.rakbank.expensems.utils.Constant;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseServiceImpl.class);

    private final ExpenseRepository expenseRepository;

    private final ExpenseMapper expenseMapper;

    private final BudgetServiceWebClient budgetClient;

    private final NotificationServiceWebClient notificationClient;


    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository, BudgetServiceWebClient budgetClient, NotificationServiceWebClient notificationClient,
                              ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
        this.budgetClient = budgetClient;
        this.notificationClient = notificationClient;
    }

    @Override
    public  ExpenseResponse addExpense(ExpenseRequest expenseRequest) {
        Expense expense = expenseRepository.save(expenseMapper.toEntity(expenseRequest));
        ExpenseResponse response = expenseMapper.toDomain(expense);
        response.setDescription(Constant.EXPENSE_ADDED);

        BudgetResponse budgetResponse = budgetClient.getBudgetCategory(expenseRequest.getCategoryName()).block();
        if (expenseRequest.getCategoryName().equalsIgnoreCase(budgetResponse.getCategoryName())
                && isBudgetExceeded(expense, budgetResponse)) {
             NotificationResponse notificationResponse = notificationClient
                     .createNotification(constructNotificationRequest(budgetResponse, expenseRequest)).block();
            response.setDescription("Expense amount exceeded. User has been notified with notificationId " +notificationResponse.getNotificationId());
        }
        return response;
    }

    @Override
    public ExpenseResponse updateExpense(String expenseId, ExpenseRequest expenseRequest) {

        Expense expense = expenseRepository.save(expenseRepository.findById(expenseId)
                .map(exp -> {
                    exp.setExpenseDesc(expenseRequest.getDescription());
                    exp.setExpenseAmt(new BigDecimal(expenseRequest.getExpenseAmount()));
                    exp.setCategory(expenseRequest.getCategoryName());
                    return exp;
                })
                .orElseThrow(() -> new RecordNotFoundException(Constant.NO_RECORDS)));

        ExpenseResponse response = expenseMapper.toDomain(expense);
        response.setDescription(Constant.EXPENSE_UPDATED);

        BudgetResponse budgetResponse = budgetClient.getBudgetCategory(expenseRequest.getCategoryName()).block();
        if (expenseRequest.getCategoryName().equalsIgnoreCase(budgetResponse.getCategoryName())
                && isBudgetExceeded(expense, budgetResponse)) {
            NotificationResponse notificationResponse = notificationClient
                    .createNotification(constructNotificationRequest(budgetResponse, expenseRequest)).block();
            response.setDescription("Expense amount exceeded. User has been notified with notificationId " +notificationResponse.getNotificationId());
        }
        return response;
    }

    private boolean isBudgetExceeded(Expense expense, BudgetResponse budgetResponse) {
        return new BigDecimal(budgetResponse.getAmount()).compareTo(expense.getExpenseAmt()) < 0;
    }


    private NotificationRequest constructNotificationRequest(BudgetResponse budgetResponse, ExpenseRequest expenseRequest) {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setExpenseAmount(expenseRequest.getExpenseAmount());
        notificationRequest.setExpenseDesc(expenseRequest.getDescription());
        notificationRequest.setBudgetAmount(budgetResponse.getAmount());
        notificationRequest.setBudgetCategory(budgetResponse.getCategoryName());
        return notificationRequest;
    }

    @Override
    public List<ExpenseResponse> getAllExpenses() {
        return expenseRepository.findAll().stream()
                .map(expense -> expenseMapper.toDomain(expense))
                .collect(Collectors.toList());
    }

    @Override
    public void removeExpense(String id) {
        expenseRepository.delete(expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseServiceException(Constant.NO_RECORDS)));
    }

    @Override
    public ExpenseResponse getExpense(String id) {
        return expenseRepository.findById(id).map(expenseMapper::toDomain)
                .orElseThrow(() -> new ExpenseServiceException(Constant.NO_RECORDS));
    }

}
