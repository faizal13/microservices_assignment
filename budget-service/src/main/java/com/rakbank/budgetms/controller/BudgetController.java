package com.rakbank.budgetms.controller;

import com.rakbank.budgetms.entity.Budget;
import com.rakbank.budgetms.model.BudgetRequest;
import com.rakbank.budgetms.model.BudgetResponse;
import com.rakbank.budgetms.model.CustomResponse;
import com.rakbank.budgetms.model.EmptyJsonResponse;
import com.rakbank.budgetms.service.BudgetService;
import com.rakbank.budgetms.utils.Constant;
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

import java.util.List;

@RestController
@RequestMapping("/api")
public class BudgetController {

    private static final Logger logger = LoggerFactory.getLogger(BudgetController.class);
    private final BudgetService budgetService;

    @Autowired
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @Operation(summary = "Add or create Budget")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added Budget Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid filed value",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))),
             })
    @PostMapping("/v1/budget")
    public ResponseEntity<CustomResponse<BudgetResponse>> addBudget(@RequestBody @Valid BudgetRequest budgetRequest, HttpServletRequest request) {
        logger.info("Request received: {}", budgetRequest);
        CustomResponse<BudgetResponse> response = new CustomResponse<>(200, Constant.REQUEST_SUCCESS,
                budgetService.addBudget(budgetRequest), request.getServletPath());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Update existing Budget")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update Budget Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid filed value",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))),
    })
    @PutMapping("/v1/budget/{budgetId}")
    public ResponseEntity<CustomResponse<BudgetResponse>> updateBudget(@PathVariable @NotNull String budgetId,
                                                                    @RequestBody @Valid BudgetRequest budgetRequest, HttpServletRequest request) {
        CustomResponse<BudgetResponse> response = new CustomResponse<>(200, Constant.REQUEST_SUCCESS,
                budgetService.updateBudget(budgetId, budgetRequest), request.getServletPath());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "get all budgets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Budgets fetched Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid filed value",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))),
    })
    @GetMapping("/v1/budgets")
    public ResponseEntity<CustomResponse<List<Budget>>> getAllBudgets(HttpServletRequest request) {
        CustomResponse<List<Budget>> response = new CustomResponse<>(200, Constant.REQUEST_SUCCESS, budgetService.getAllBudgets(), request.getServletPath());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "get Budget by Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "fetched Budget Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid filed value",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))),
    })
    @GetMapping("/v1/budget/{categoryName}")
    public ResponseEntity<CustomResponse<Object>> getBudget(@PathVariable String categoryName, HttpServletRequest request) {
        CustomResponse<Object> response;
        BudgetResponse budgetResponse = budgetService.getBudget(categoryName);
        if(null == budgetResponse){
             response = new CustomResponse<>(200, Constant.NO_RECORDS, new EmptyJsonResponse(), request.getServletPath());
        }else{
            response = new CustomResponse<>(200, Constant.REQUEST_SUCCESS, budgetService.getBudget(categoryName), request.getServletPath());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "delete a Budget")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Budget deleted Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid filed value",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))),
    })
    @DeleteMapping("/v1/budget/{id}")
    public ResponseEntity<CustomResponse<String>> deleteBudget(@PathVariable String  id, HttpServletRequest request) {
        budgetService.removeBudget(id);
        CustomResponse<String> response = new CustomResponse<>(200, Constant.REQUEST_SUCCESS, Constant.REQUEST_SUCCESS, request.getServletPath());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
