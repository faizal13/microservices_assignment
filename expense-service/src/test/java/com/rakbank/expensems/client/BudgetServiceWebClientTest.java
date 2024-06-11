package com.rakbank.expensems.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakbank.expensems.exception.BadRequestCustomException;
import com.rakbank.expensems.exception.ExpenseWebClientException;
import com.rakbank.expensems.model.BudgetResponse;
import com.rakbank.expensems.model.CustomResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BudgetServiceWebClientTest {

    @Mock
    private WebClient.Builder loadBalanced;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private BudgetServiceWebClient budgetServiceWebClient;

    private BudgetResponse budgetResponse;
    private CustomResponse customResponse;

    @Value("${budget.service.base-url}")
    private String baseUrl = "http://localhost:8080";

    @Value("${budget.service.path}")
    private String path = "/budget";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        budgetServiceWebClient = new BudgetServiceWebClient();
        budgetServiceWebClient.mapper = mapper;
        ReflectionTestUtils.setField(budgetServiceWebClient, "baseUrl", baseUrl);
        ReflectionTestUtils.setField(budgetServiceWebClient, "path", path);
        //budgetServiceWebClient.baseUrl = baseUrl;
        //budgetServiceWebClient.path = path;

        budgetServiceWebClient.setLoadBalanced(loadBalanced);

        budgetResponse = new BudgetResponse();
        budgetResponse.setCategoryName("Food");
        budgetResponse.setAmount(1000d);

        customResponse = new CustomResponse();
        customResponse.setData(budgetResponse);

        when(loadBalanced.build()).thenReturn(webClient);
    }

    @Test
    void testGetBudgetCategorySuccess() {
        String budgetCategory = "Food";

        doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersUriSpec).when(requestHeadersUriSpec).uri(baseUrl + path + "/" + budgetCategory);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CustomResponse.class)).thenReturn(Mono.just(customResponse));
        when(mapper.convertValue(customResponse.getData(), BudgetResponse.class)).thenReturn(budgetResponse);

        Mono<BudgetResponse> result = budgetServiceWebClient.getBudgetCategory(budgetCategory);

        StepVerifier.create(result)
                .expectNext(budgetResponse)
                .verifyComplete();

        verify(webClient).get();
        verify(requestHeadersUriSpec).uri(baseUrl + path + "/" + budgetCategory);
        verify(requestHeadersUriSpec).retrieve();
        verify(responseSpec).bodyToMono(CustomResponse.class);
        verify(mapper).convertValue(customResponse.getData(), BudgetResponse.class);
    }

    @Test
    void testGetBudgetCategoryBadRequest() {
        String budgetCategory = "Food";

        doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersUriSpec).when(requestHeadersUriSpec).uri(baseUrl + path + "/" + budgetCategory);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CustomResponse.class)).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "400")));

        Mono<BudgetResponse> result = budgetServiceWebClient.getBudgetCategory(budgetCategory);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof BadRequestCustomException &&
                        throwable.getMessage().contains("Bad request/Incorrect request parameter/Missing request parameter."))
                .verify();

        verify(webClient).get();
        verify(requestHeadersUriSpec).uri(baseUrl + path + "/" + budgetCategory);
        verify(requestHeadersUriSpec).retrieve();
        verify(responseSpec).bodyToMono(CustomResponse.class);
    }

    @Test
    void testGetBudgetCategoryCategoryNotFound() {
        String budgetCategory = "UnknownCategory";

        doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersUriSpec).when(requestHeadersUriSpec).uri(baseUrl + path + "/" + budgetCategory);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CustomResponse.class)).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "512")));

        Mono<BudgetResponse> result = budgetServiceWebClient.getBudgetCategory(budgetCategory);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ExpenseWebClientException &&
                        throwable.getMessage().contains("no such category exist. Please Create Budget for category"))
                .verify();

        verify(webClient).get();
        verify(requestHeadersUriSpec).uri(baseUrl + path + "/" + budgetCategory);
        verify(requestHeadersUriSpec).retrieve();
        verify(responseSpec).bodyToMono(CustomResponse.class);
    }

    @Test
    void testGetBudgetCategoryServiceUnavailable() {
        String budgetCategory = "Food";

        doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersUriSpec).when(requestHeadersUriSpec).uri(baseUrl + path + "/" + budgetCategory);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CustomResponse.class)).thenReturn(Mono.error(new Exception("503 Service Unavailable")));

        Mono<BudgetResponse> result = budgetServiceWebClient.getBudgetCategory(budgetCategory);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof HttpServerErrorException &&
                        throwable.getMessage().contains("Budget microservice is down. Please keep the instance running"))
                .verify();

        verify(webClient).get();
        verify(requestHeadersUriSpec).uri(baseUrl + path + "/" + budgetCategory);
        verify(requestHeadersUriSpec).retrieve();
        verify(responseSpec).bodyToMono(CustomResponse.class);
    }
}