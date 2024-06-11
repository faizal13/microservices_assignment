/*
package com.rakbank.expensems.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakbank.expensems.exception.BadRequestCustomException;
import com.rakbank.expensems.model.CustomResponse;
import com.rakbank.expensems.model.NotificationRequest;
import com.rakbank.expensems.model.NotificationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceWebClientTest {

    @Mock
    private WebClient.Builder loadBalanced;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private NotificationServiceWebClient notificationServiceWebClient;

    private NotificationRequest notificationRequest;
    private NotificationResponse notificationResponse;
    private CustomResponse customResponse;

    @Value("${notification.service.base-url}")
    private String baseUrl = "http://localhost:8080";

    @Value("${notification.service.path}")
    private String path = "/notification";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        notificationServiceWebClient = new NotificationServiceWebClient();
        notificationServiceWebClient.setLoadBalanced(loadBalanced);
        ReflectionTestUtils.setField(notificationServiceWebClient, "baseUrl", baseUrl);
        ReflectionTestUtils.setField(notificationServiceWebClient, "path", path);

        notificationRequest = new NotificationRequest();
        notificationRequest.setExpenseAmount(500);
        notificationRequest.setExpenseDesc("Dinner");

        notificationResponse = new NotificationResponse();
        notificationResponse.setNotificationId(12345l);

        customResponse = new CustomResponse();
        customResponse.setData(notificationResponse);

        when(loadBalanced.build()).thenReturn(webClient);
    }

    @Test
    void testCreateNotificationSuccess() {
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(baseUrl + path)).thenReturn(requestBodyUriSpec);
        //doReturn(requestHeadersSpec).when(requestBodyUriSpec).body(ArgumentMatchers.any(), eq(NotificationRequest.class));
        doReturn(requestHeadersSpec).when(requestBodyUriSpec).body(any(Mono.class));
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CustomResponse.class)).thenReturn(Mono.just(customResponse));
        when(mapper.convertValue(customResponse.getData(), NotificationResponse.class)).thenReturn(notificationResponse);

        Mono<NotificationResponse> result = notificationServiceWebClient.createNotification(notificationRequest);

        StepVerifier.create(result)
                .expectNext(notificationResponse)
                .verifyComplete();

        verify(webClient).post();
        verify(requestBodyUriSpec).uri(baseUrl + path);
        verify(requestBodyUriSpec).body(any());
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToMono(CustomResponse.class);
        verify(mapper).convertValue(customResponse.getData(), NotificationResponse.class);
    }

    @Test
    void testCreateNotificationBadRequest() {
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(baseUrl + path)).thenReturn(requestBodyUriSpec);
        doReturn(requestHeadersSpec).when(requestBodyUriSpec).body(any(), eq(NotificationRequest.class));
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CustomResponse.class)).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "400")));

        Mono<NotificationResponse> result = notificationServiceWebClient.createNotification(notificationRequest);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof BadRequestCustomException &&
                        throwable.getMessage().contains("Bad request/Incorrect request parameter/Missing request parameter."))
                .verify();

        verify(webClient).post();
        verify(requestBodyUriSpec).uri(baseUrl + path);
        verify(requestBodyUriSpec).body(any());
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToMono(CustomResponse.class);
    }

    @Test
    void testCreateNotificationServiceUnavailable() {
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(baseUrl + path)).thenReturn(requestBodyUriSpec);
        doReturn(requestHeadersSpec).when(requestBodyUriSpec).body(any(), eq(NotificationRequest.class));
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CustomResponse.class)).thenReturn(Mono.error(new Exception("503 Service Unavailable")));

        Mono<NotificationResponse> result = notificationServiceWebClient.createNotification(notificationRequest);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof HttpServerErrorException &&
                        throwable.getMessage().contains("Notification microservice is down. Please keep the instance running"))
                .verify();

        verify(webClient).post();
        verify(requestBodyUriSpec).uri(baseUrl + path);
        verify(requestBodyUriSpec).body(any());
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToMono(CustomResponse.class);
    }
}

*/
