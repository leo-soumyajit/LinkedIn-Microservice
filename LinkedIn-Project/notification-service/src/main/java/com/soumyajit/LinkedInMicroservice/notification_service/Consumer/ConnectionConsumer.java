package com.soumyajit.LinkedInMicroservice.notification_service.Consumer;

import com.soumyajit.LinkedInMicroservice.connectionService.Events.ConnectionAcceptEvent;
import com.soumyajit.LinkedInMicroservice.connectionService.Events.ConnectionRequestEvent;
import com.soumyajit.LinkedInMicroservice.notification_service.Entities.Notification;
import com.soumyajit.LinkedInMicroservice.notification_service.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "accept_connection_topic")
    public void handleAcceptConnectionTopics(ConnectionAcceptEvent connectionAcceptEvent){
        log.info("handle connection acceptation : {}",connectionAcceptEvent);

        String message = String.format("Connection request accepted, send by : %s",
                connectionAcceptEvent.getSenderId());
        Notification notification = Notification.builder()
                .user_id(connectionAcceptEvent.getReceiverId())
                .message(message)
                .build();

        notificationService.addNotification(notification);
    }

    @KafkaListener(topics = "request_connection_topic")
    public void handleRequestConnectionTopics(ConnectionRequestEvent connectionRequestEvent){
        log.info("Handle Connection request : {}",connectionRequestEvent);

        String message = String.format("User id : %s You have a new connection request, send by : %s",
                connectionRequestEvent.getReceiverId(),connectionRequestEvent.getSenderId());

        Notification notification = Notification.builder()
                .message(message)
                .user_id(connectionRequestEvent.getReceiverId())
                .build();
        notificationService.addNotification(notification);
    }

}
