package com.soumyajit.LinkedInMicroservice.postService.Service;

import com.soumyajit.LinkedInMicroservice.postService.Auth.AuthContextHolder;
import com.soumyajit.LinkedInMicroservice.postService.Entities.Post;
import com.soumyajit.LinkedInMicroservice.postService.Entities.PostLike;
import com.soumyajit.LinkedInMicroservice.postService.Events.PostLikedEvent;
import com.soumyajit.LinkedInMicroservice.postService.Exceptions.BadRequestException;
import com.soumyajit.LinkedInMicroservice.postService.Exceptions.ResourceNotFound;
import com.soumyajit.LinkedInMicroservice.postService.Repository.postLikeRepository;
import com.soumyajit.LinkedInMicroservice.postService.Repository.postRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class postLikeService {
    private final postLikeRepository postLikeRepository;
    private final postRepository postRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<Long, PostLikedEvent> postLikedEventKafkaTemplate;


    @Transactional
    public void likePost(Long postId) throws BadRequestException {
        Long userId = AuthContextHolder.getCurrentUserId();
        log.info("Liking the post with postId : {}",postId);

        Post post = postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFound("Post with id "+postId+" not found"));

        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(hasAlreadyLiked) {
            throw new BadRequestException("You have already liked this post");
        }

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        log.info("saving the post after user liked");
        postLikeRepository.save(postLike);

        //TODO: Send notifications to the user

        PostLikedEvent postLikedEvent = PostLikedEvent.builder()
                .likedByUserId(userId)
                .ownerUserId(post.getUserId())
                .postId(postId)
                .build();
        postLikedEventKafkaTemplate.send("post_liked_topic",postLikedEvent);

    }

    @Transactional
    public void unlikePost(Long postId) {
        Long userId = AuthContextHolder.getCurrentUserId();
        log.info("Unliking the post with postId : {}",postId);

        postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFound("Post with id "+postId+" not found"));

        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(!hasAlreadyLiked) {
            throw new BadRequestException("You have not liked this post");
        }

        log.info("delete the like");
        postLikeRepository.deleteByUserIdAndPostId(userId,postId);

    }
}
