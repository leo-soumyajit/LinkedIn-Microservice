package com.soumyajit.LinkedInMicroservice.postService.Repository;

import com.soumyajit.LinkedInMicroservice.postService.Entities.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface postLikeRepository extends JpaRepository<PostLike,Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    void deleteByUserIdAndPostId(Long userId, Long postId);
}
