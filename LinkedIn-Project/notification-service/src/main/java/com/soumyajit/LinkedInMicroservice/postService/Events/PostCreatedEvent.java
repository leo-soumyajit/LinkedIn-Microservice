package com.soumyajit.LinkedInMicroservice.postService.Events;

import lombok.Builder;
import lombok.Data;

@Data
public class PostCreatedEvent {
    private Long ownerUserId;
    private Long postId;
    private Long userId;
    private String userName;
    private String imageUrl;
    private String content;

}
