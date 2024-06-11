package com.rakbank.expensems.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakbank.expensems.exception.BadRequestCustomException;
import com.rakbank.expensems.exception.ExpenseWebClientException;
import com.rakbank.expensems.model.CustomResponse;
import com.rakbank.expensems.model.NotificationRequest;
import com.rakbank.expensems.model.NotificationResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import reactor.core.publisher.Mono;

@Service
@CircuitBreaker(name = "NotificationService")
public class NotificationServiceWebClient extends AbstractWebClient {

    private static final Logger log = LoggerFactory.getLogger(AbstractWebClient.class);

    @Value("${notification.service.base-url}")
    private String baseUrl;

    @Value("${notification.service.path}")
    private String path;

    @Autowired
    ObjectMapper mapper;

    public Mono<NotificationResponse> createNotification(NotificationRequest notificationRequest) {
        log.info("NotificationWebClient createNotification request: {}", notificationRequest);

        return postNoAuth(baseUrl + path,
                notificationRequest, CustomResponse.class)
                .flatMap(response -> Mono.just(mapper.convertValue(response.getData(), NotificationResponse.class)))
                .onErrorResume(e -> {
                    if(e instanceof IllegalArgumentException){
                        return Mono.error(new ExpenseWebClientException("error while processing notification service response"));
                    }
                    else if (e.getMessage().contains("400")) {
                        return Mono.error(new BadRequestCustomException("Bad request/Incorrect request parameter/Missing request parameter."));
                    } else {
                        log.error("NotificationWebClient createNotification error {}", e.getMessage());
                        return Mono.error(new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE,
                                "Notification microservice is down. Please keep the instance running"));
                    }
                });
    }

}
