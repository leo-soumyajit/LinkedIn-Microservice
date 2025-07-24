package com.soumyajit.LinkedInMicroservice.postService.Events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostLikedEvent {
    private Long postId;
    private Long ownerUserId;
    private Long likedByUserId;
}
