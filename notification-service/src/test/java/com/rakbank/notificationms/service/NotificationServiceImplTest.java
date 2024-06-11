package com.rakbank.notificationms.service;

import com.rakbank.notificationms.entity.Notification;
import com.rakbank.notificationms.mapper.NotificationMapper;
import com.rakbank.notificationms.model.NotificationRequest;
import com.rakbank.notificationms.model.NotificationResponse;
import com.rakbank.notificationms.repository.NotificationRepository;
import com.rakbank.notificationms.service.serviceImpl.NotificationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationMapper notificationMapper;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    void testCreateNotification_Success() {
        NotificationRequest notificationRequest = new NotificationRequest();
        Notification notification = new Notification();
        when(notificationMapper.toEntity(any())).thenReturn(notification);
        when(notificationRepository.save(any())).thenReturn(notification);
        when(notificationMapper.toDomain(any())).thenReturn(new NotificationResponse());

        NotificationResponse response = notificationService.createNotification(notificationRequest);

        assertNotNull(response);
        verify(notificationRepository, times(1)).save(any());
    }

    @Test
    void testGetAllNotifications_Success() {
        List<Notification> notifications = Collections.singletonList(new Notification());
        when(notificationRepository.findAll()).thenReturn(notifications);
        when(notificationMapper.toDomain(any())).thenReturn(new NotificationResponse());

        List<NotificationResponse> result = notificationService.getAllNotifications();

        assertFalse(result.isEmpty());
        assertEquals(notifications.size(), result.size());
        verify(notificationRepository, times(1)).findAll();
    }

    @Test
    void testGetAllNotifications_EmptyList() {
        when(notificationRepository.findAll()).thenReturn(Collections.emptyList());

        List<NotificationResponse> result = notificationService.getAllNotifications();

        assertTrue(result.isEmpty());
        verify(notificationRepository, times(1)).findAll();
    }

    @Test
    void testCreateNotification_Failure() {
        NotificationRequest notificationRequest = new NotificationRequest();
        when(notificationMapper.toEntity(any())).thenReturn(new Notification());
        when(notificationRepository.save(any())).thenThrow(new RuntimeException("Failed to save notification"));

        assertThrows(RuntimeException.class, () -> notificationService.createNotification(notificationRequest));
        verify(notificationRepository, times(1)).save(any());
    }
}

