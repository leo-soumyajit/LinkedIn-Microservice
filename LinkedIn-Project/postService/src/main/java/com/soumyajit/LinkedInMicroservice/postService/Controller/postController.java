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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
@Slf4j
public class postController {

    private final postService postService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<postDto> createPost(@RequestParam("post") String content,
                                              HttpServletRequest httpServletRequest,
                                              @RequestParam MultipartFile multipartFile
                                              ) throws IOException {
        return new ResponseEntity<>(postService.createPost(content,
                AuthContextHolder.getCurrentUserId(),multipartFile),
                HttpStatus.CREATED);
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
