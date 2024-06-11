package com.rakbank.notificationms.service.serviceImpl;


import com.rakbank.notificationms.mapper.NotificationMapper;
import com.rakbank.notificationms.model.NotificationRequest;
import com.rakbank.notificationms.model.NotificationResponse;
import com.rakbank.notificationms.repository.NotificationRepository;
import com.rakbank.notificationms.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final NotificationRepository notificationRepository;


    private final NotificationMapper notificationMapper;


    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
    }



    public NotificationResponse createNotification(NotificationRequest notificationRequest) {

       return Optional.of(notificationRepository
               .save(notificationMapper.toEntity(notificationRequest)))
               .map(notificationMapper::toDomain).get();
    }

    public List<NotificationResponse> getAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(notification -> notificationMapper.toDomain(notification))
                .collect(Collectors.toList());

    }

}
