package com.soumyajit.LinkedInMicroservice.notification_service.Repository;


import com.soumyajit.LinkedInMicroservice.notification_service.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
