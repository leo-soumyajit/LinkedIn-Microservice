package com.soumyajit.LinkedInMicroservice.postService.Service;

import com.soumyajit.LinkedInMicroservice.postService.Advices.ApiResponse;
import com.soumyajit.LinkedInMicroservice.postService.Client.ConnectionServiceClient;
import com.soumyajit.LinkedInMicroservice.postService.Client.UploaderServiceClient;
import com.soumyajit.LinkedInMicroservice.postService.DTOS.Person;
import com.soumyajit.LinkedInMicroservice.postService.DTOS.postCreateRequestDto;
import com.soumyajit.LinkedInMicroservice.postService.DTOS.postDto;
import com.soumyajit.LinkedInMicroservice.postService.Entities.Post;
import com.soumyajit.LinkedInMicroservice.postService.Events.PostCreatedEvent;
import com.soumyajit.LinkedInMicroservice.postService.Exceptions.ResourceNotFound;
import com.soumyajit.LinkedInMicroservice.postService.Repository.postRepository;
import com.soumyajit.LinkedInMicroservice.postService.Auth.AuthContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class postService {
    private final postRepository postRepository;
    private final ModelMapper modelMapper;
    private final ConnectionServiceClient ConnectionServiceClient;
    private final UploaderServiceClient uploaderServiceClient;
    private final KafkaTemplate<Long, PostCreatedEvent> postCreatedEventKafkaTemplate;

    public postDto createPost(String content, Long userId,
                              MultipartFile multipartFile)
    {
        log.info("Creating post for user with userId: {}",userId);
        Post post = new Post();

        String url = String.valueOf(uploaderServiceClient.upload(multipartFile));
        log.info("Getting the url from uploader service : {}",url);

        post.setUserId(userId);
        post.setUrl(url);
        post.setContent(content);
        log.info("Saving post for user with userId: {}",userId);
        Post savedPost = postRepository.save(post);
        log.info("Successfully saved the post");

        ApiResponse<List<Person>> response = ConnectionServiceClient.getFirstDegreeConnection(AuthContextHolder.getCurrentUserId());
        List<Person> personList = response.getData();

        for(Person person : personList){ // send notification to each connection
            PostCreatedEvent postCreatedEvent = PostCreatedEvent.builder()
                    .postId(post.getId())
                    .userId(person.getUserId())
                    .userName(person.getName())
                    .content(content)
                    .ownerUserId(userId)
                    .imageUrl(url)
                    .build();
            postCreatedEventKafkaTemplate.send("post_created_topic",postCreatedEvent);
        }
        log.info("Successfully send notification to each connection");

        return modelMapper.map(savedPost,postDto.class);
    }

    public postDto getPostById(Long postId) {
        log.info("Getting post with postId: {}",postId);
        //get userId
        AuthContextHolder.getCurrentUserId();

        log.info("User id is : {}",AuthContextHolder.getCurrentUserId());
        Post post = postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFound("post with id "+postId+" not found"));
        return modelMapper.map(post, postDto.class);
    }

    public List<postDto> getAllPostsOfUser(Long userId) {
        log.info("Getting all the posts of a User with Id: {}",userId);
        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream().map(post ->
                        modelMapper.map(post,postDto.class))
                .collect(Collectors.toList());
    }


    //TODO : for all posts inside feed
    public List<postDto> getAllPosts() {
        log.info("Getting all the posts");
        return postRepository.findAll().stream().map(post ->
                        modelMapper.map(post,postDto.class))
                .collect(Collectors.toList());
    }
}
