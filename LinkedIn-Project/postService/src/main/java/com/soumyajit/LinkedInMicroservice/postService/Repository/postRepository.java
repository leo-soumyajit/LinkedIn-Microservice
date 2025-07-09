package com.soumyajit.LinkedInMicroservice.postService.Repository;

import com.soumyajit.LinkedInMicroservice.postService.Entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface postRepository extends JpaRepository<Post,Long> {

    List<Post> findByUserId(Long userId);
}
