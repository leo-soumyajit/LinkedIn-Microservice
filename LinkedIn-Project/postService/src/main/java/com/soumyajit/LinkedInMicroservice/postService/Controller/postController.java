package com.soumyajit.LinkedInMicroservice.postService.Controller;

import com.soumyajit.LinkedInMicroservice.postService.Auth.AuthContextHolder;
import com.soumyajit.LinkedInMicroservice.postService.DTOS.postCreateRequestDto;
import com.soumyajit.LinkedInMicroservice.postService.DTOS.postDto;
import com.soumyajit.LinkedInMicroservice.postService.Service.postService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
@Slf4j
public class postController {

    private final postService postService;

    @PostMapping
    public ResponseEntity<postDto> createPost(@RequestBody postCreateRequestDto postCreateRequestDto,
                                              HttpServletRequest httpServletRequest
    ){
        ;
        return new ResponseEntity<>(postService.createPost(postCreateRequestDto, AuthContextHolder.getCurrentUserId()), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<postDto> getPost(@PathVariable Long postId){

        postDto postDto = postService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/user/{userId}/allPosts")
    public ResponseEntity<List<postDto>> getAllPostsOfUser(@PathVariable Long userId){
        return ResponseEntity.ok(postService.getAllPostsOfUser(userId));
    }

}
