package com.soumyajit.LinkedInMicroservice.notification_service.Clients;

import com.soumyajit.LinkedInMicroservice.notification_service.Advices.ApiResponse;
import com.soumyajit.LinkedInMicroservice.notification_service.DTOS.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(
        name = "user-service",
        path = "/users/auth"
)
public interface getUserClient {

    @GetMapping("/{userId}")
    ApiResponse<UserDto> getUserById(@PathVariable("userId") Long userId);

}
