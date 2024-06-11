package com.rakbank.notificationms.controller;

import com.rakbank.notificationms.model.CustomResponse;
import com.rakbank.notificationms.model.NotificationRequest;
import com.rakbank.notificationms.model.NotificationResponse;
import com.rakbank.notificationms.service.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    void testCreateNotification_Success() {
        NotificationRequest notificationRequest = new NotificationRequest();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(notificationService.createNotification(notificationRequest)).thenReturn(new NotificationResponse());

        ResponseEntity<CustomResponse<NotificationResponse>> responseEntity = notificationController.createNotification(notificationRequest, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(notificationService, times(1)).createNotification(notificationRequest);
        verify(request, times(1)).getServletPath();
    }

    @Test
    void testGetAllNotifications_Success() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        List<NotificationResponse> notificationResponses = Collections.singletonList(new NotificationResponse());
        when(notificationService.getAllNotifications()).thenReturn(notificationResponses);

        ResponseEntity<CustomResponse<List<NotificationResponse>>> responseEntity = notificationController.getAllNotifications(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(notificationService, times(1)).getAllNotifications();
        verify(request, times(1)).getServletPath();
    }
}

