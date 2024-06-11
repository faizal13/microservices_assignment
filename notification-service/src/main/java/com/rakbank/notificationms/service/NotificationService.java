package com.rakbank.notificationms.service;

import com.rakbank.notificationms.model.NotificationRequest;
import com.rakbank.notificationms.model.NotificationResponse;

import java.util.List;

public interface NotificationService {

    NotificationResponse createNotification(NotificationRequest notificationRequest);

    List<NotificationResponse> getAllNotifications();

}
