package com.rakbank.notificationms.repository;

import com.rakbank.notificationms.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findById(Long id);


}
