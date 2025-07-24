package com.soumyajit.LinkedInMicroservice.notification_service.Consumer;

import com.soumyajit.LinkedInMicroservice.notification_service.Entities.Notification;
import com.soumyajit.LinkedInMicroservice.notification_service.Service.NotificationService;
import com.soumyajit.LinkedInMicroservice.postService.Events.PostCreatedEvent;
import com.soumyajit.LinkedInMicroservice.postService.Events.PostLikedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "post_created_topic")
    public void handlePostCreated(PostCreatedEvent postCreatedEvent){
        log.info("handlePostCreated : {}",postCreatedEvent);

        String message = String.format("Your connections with name : %s has created this post : %s",
                postCreatedEvent.getOwnerUserId(), postCreatedEvent.getContent());

        Notification notification = Notification.builder()
                .message(message)
                .user_id(postCreatedEvent.getUserId())
                .build();

        notificationService.addNotification(notification);
    }

    @KafkaListener(topics = "post_liked_topic")
    public void handlePostLiked(PostLikedEvent postLikedEvent){
        log.info("handlePostLiked : {}",postLikedEvent);

        String message = String.format("User with name : %s has liked your post : %s",
                postLikedEvent.getLikedByUserId() , postLikedEvent.getPostId());

        Notification notification = Notification.builder()
                .message(message)
                .user_id(postLikedEvent.getOwnerUserId())
                .build();
        notificationService.addNotification(notification);
    }
}
