package com.soumyajit.LinkedInMicroservice.notification_service.Service;

import com.soumyajit.LinkedInMicroservice.notification_service.Entities.Notification;
import com.soumyajit.LinkedInMicroservice.notification_service.Repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void addNotification(Notification notification){
        log.info("Adding notification to db, message : {}",
                notification.getMessage());
        notificationRepository.save(notification);

        //can send email
    }
}
