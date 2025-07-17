package com.soumyajit.LinkedInMicroservice.postService.Client;

import com.soumyajit.LinkedInMicroservice.postService.DTOS.Person;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "connection-service", path = "/connections")
public interface ConnectionServiceClient {

    @GetMapping("/core/{userId}/first-degree")
    List<Person> getFirstDegreeConnection(@PathVariable Long userId);

}
