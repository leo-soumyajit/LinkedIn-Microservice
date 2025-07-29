package com.soumyajit.LinkedInMicroservice.notification_service.Consumer;

import com.soumyajit.LinkedInMicroservice.notification_service.Advices.ApiResponse;
import com.soumyajit.LinkedInMicroservice.notification_service.Clients.getUserClient;
import com.soumyajit.LinkedInMicroservice.notification_service.DTOS.UserDto;
import com.soumyajit.LinkedInMicroservice.notification_service.EmailService.EmailService;
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
    private final getUserClient getUserClient;
    private final EmailService emailService;

    @KafkaListener(topics = "post_created_topic")
    public void handlePostCreated(PostCreatedEvent postCreatedEvent) {
        log.info("handlePostCreated : {}", postCreatedEvent);

        // 1. Save notification to DB
        String message = String.format(
                "Your connection with ID %s has created this post: %s",
                postCreatedEvent.getOwnerUserId(),
                postCreatedEvent.getContent()
        );

        Notification notification = Notification.builder()
                .message(message)
                .user_id(postCreatedEvent.getUserId())
                .userName(postCreatedEvent.getUserName())
                .imageUrl(postCreatedEvent.getImageUrl())
                .build();

        notificationService.addNotification(notification);
        log.info("Notification saved for userId: {}", postCreatedEvent.getUserId());

        // 2. Fetch recipient user info (email, name)
        log.info("Fetching users email and name : ");
        try {
            ApiResponse<UserDto> response = getUserClient.getUserById(postCreatedEvent.getUserId());
            UserDto user = response.getData();


            log.info("Successfully fetched users email {} and name : {}",user.getEmail(), user.getName());

            if (user != null && user.getEmail() != null) {
                String subject = "New Post from Your Connection!";
                String body = String.format(
                        "Hi %s,\n\nYour connection (User ID: %d) just posted:\n\n\"%s\"\n\nCheers,\nLinkedIn Clone",
                        user.getName(),
                        postCreatedEvent.getOwnerUserId(),
                        postCreatedEvent.getContent()
                );

                // 3. Send the email
                String htmlBody = emailService.generateHtmlEmail(postCreatedEvent.getUserName(), postCreatedEvent.getOwnerUserId(), postCreatedEvent.getContent(), postCreatedEvent.getImageUrl());
//                emailService.sendSimpleEmail(user.getEmail(), subject, body);
                emailService.sendHtmlEmail(user.getEmail(), "New Post from Your Connection", htmlBody);
                log.info("Email sent to {}", user.getEmail());
            } else {
                log.warn("User not found or email is null for userId: {}", postCreatedEvent.getUserId());
            }
        } catch (Exception e) {
            log.error("Error while sending email for userId: {}", postCreatedEvent.getUserId(), e);
        }
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
