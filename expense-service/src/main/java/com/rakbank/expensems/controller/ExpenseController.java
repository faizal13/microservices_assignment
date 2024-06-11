package com.rakbank.expensems.controller;

import com.rakbank.expensems.model.*;
import com.rakbank.expensems.service.ExpenseService;
import com.rakbank.expensems.utils.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);
    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }


    @Operation(summary = "Add or create Expense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added Expense Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid filed value",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))),
    })
    @PostMapping("/v1/expense")
    public ResponseEntity<CustomResponse<ExpenseResponse>> addExpense(@RequestBody @Valid ExpenseRequest expenseRequest, HttpServletRequest request) {
        ExpenseResponse expenseResponse = expenseService.addExpense(expenseRequest);
        CustomResponse<ExpenseResponse> response = new CustomResponse<>(200, expenseResponse.getDescription(),
                     expenseResponse, request.getServletPath());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "update Expense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expense updated Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid filed value",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))),
    })
    @PutMapping("/v1/expense/{expenseId}")
    public ResponseEntity<CustomResponse<ExpenseResponse>> updateExpense(@PathVariable @NotNull String expenseId,
                                                                      @RequestBody @Valid ExpenseRequest expenseRequest, HttpServletRequest request) {
        ExpenseResponse expenseResponse = expenseService.updateExpense(expenseId, expenseRequest);
        CustomResponse<ExpenseResponse> response = new CustomResponse<>(200, expenseResponse.getDescription(),
                expenseResponse, request.getServletPath());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "get all Expenses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expenses fetched Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid filed value",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))),
    })
    @GetMapping("/v1/expenses")
    public ResponseEntity<CustomResponse<List<ExpenseResponse>>> getAllExpenses(HttpServletRequest request) {
        CustomResponse<List<ExpenseResponse>> response = new CustomResponse<>(200, Constant.REQUEST_SUCCESS,
                expenseService.getAllExpenses(), request.getServletPath());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "Get Expense by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "fetched Expense Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid filed value",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))),
    })
    @GetMapping("/v1/expense/{expenseId}")
    public ResponseEntity<CustomResponse<ExpenseResponse>> getExpense(@PathVariable String expenseId, HttpServletRequest request) {
        CustomResponse<ExpenseResponse> response = new CustomResponse<>(200, "Success", expenseService.getExpense(expenseId), request.getServletPath());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "delete Expense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expense deleted Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid filed value",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))),
    })
    @DeleteMapping("/v1/expense/{id}")
    public ResponseEntity<CustomResponse<String>> deleteExpense(@PathVariable String id, HttpServletRequest request) {
        expenseService.removeExpense(id);
        CustomResponse<String> response = new CustomResponse<>(200, Constant.REQUEST_SUCCESS, Constant.REQUEST_SUCCESS, request.getServletPath());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
