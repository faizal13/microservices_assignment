package com.rakbank.expensems.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakbank.expensems.exception.BadRequestCustomException;
import com.rakbank.expensems.exception.BudgetClientException;
import com.rakbank.expensems.exception.ExpenseServiceException;
import com.rakbank.expensems.exception.ExpenseWebClientException;
import com.rakbank.expensems.model.BudgetResponse;
import com.rakbank.expensems.model.CustomResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import reactor.core.publisher.Mono;

@Service
@CircuitBreaker(name = "BudgetService")
public class BudgetServiceWebClient extends AbstractWebClient {

    private static final Logger log = LoggerFactory.getLogger(AbstractWebClient.class);

    @Value("${budget.service.base-url}")
    private String baseUrl;

    @Value("${budget.service.path}")
    private String path;

    @Autowired
    ObjectMapper mapper;

    @Retry(name = "throwingException")
    public Mono<BudgetResponse> getBudgetCategory(String budgetCategory) {
        log.info("BudgetServiceWebClient getBudgetCategory request: {}", budgetCategory);
        return get(baseUrl + path + "/" + budgetCategory,
                CustomResponse.class)
                .flatMap(response -> Mono.just(mapper.convertValue(response.getData(), BudgetResponse.class)))
                .onErrorResume(e -> {
                    if(e instanceof IllegalArgumentException){
                        return Mono.error(new ExpenseWebClientException("error while processing budget service response"));
                    }
                    else if (e.getMessage().contains("400")) {
                        return Mono.error(new BadRequestCustomException("Bad request/Incorrect request parameter/Missing request parameter."));
                    } else if (e.getMessage().contains("512")) {
                        return Mono.error(new ExpenseWebClientException("no such category exist. Please Create Budget for category"));
                    } else{
                        log.error("BudgetServiceWebClient getBudgetCategory error {}", e.getMessage());
                        return Mono.error(new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE,
                                "Budget microservice is down. Please keep the instance running"));
                    }
                });
    }

}
