package com.soumyajit.LinkedInMicroservice.postService.Events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCreatedEvent {
    private Long ownerUserId;
    private Long postId;
    private Long userId;
    private String content;

}
