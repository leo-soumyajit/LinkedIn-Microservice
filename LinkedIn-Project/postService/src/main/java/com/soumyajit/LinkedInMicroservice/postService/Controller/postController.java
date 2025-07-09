package com.soumyajit.LinkedInMicroservice.postService.Controller;

import com.soumyajit.LinkedInMicroservice.postService.DTOS.postCreateRequestDto;
import com.soumyajit.LinkedInMicroservice.postService.DTOS.postDto;
import com.soumyajit.LinkedInMicroservice.postService.Service.postService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class postController {

    private final postService postService;

    @PostMapping
    public ResponseEntity<postDto> createPost(@RequestBody postCreateRequestDto postCreateRequestDto,
                                              HttpServletRequest httpServletRequest
    ){
        return new ResponseEntity<>(postService.createPost(postCreateRequestDto, 1L), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<postDto> getPost(@PathVariable Long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @GetMapping("/user/{userId}/allPosts")
    public ResponseEntity<List<postDto>> getAllPostsOfUser(@PathVariable Long userId){
        return ResponseEntity.ok(postService.getAllPostsOfUser(userId));
    }

}
