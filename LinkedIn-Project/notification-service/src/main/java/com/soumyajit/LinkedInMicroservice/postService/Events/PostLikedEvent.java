package com.soumyajit.LinkedInMicroservice.postService.Events;

import lombok.Builder;
import lombok.Data;

@Data
public class PostLikedEvent {
    private Long postId;
    private Long ownerUserId;
    private Long likedByUserId;
}
