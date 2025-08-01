package com.soumyajit.LinkedInMicroservice.userService.Controller;

import com.soumyajit.LinkedInMicroservice.userService.Advices.ApiResponse;
import com.soumyajit.LinkedInMicroservice.userService.DTOS.LoginRequestDto;
import com.soumyajit.LinkedInMicroservice.userService.DTOS.SignupRequestDto;
import com.soumyajit.LinkedInMicroservice.userService.DTOS.UserDto;
import com.soumyajit.LinkedInMicroservice.userService.Entities.User;
import com.soumyajit.LinkedInMicroservice.userService.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupRequestDto signupRequestDto){
        UserDto userDto = authService.signup(signupRequestDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequestDto loginRequestDto){
        String token = authService.login(loginRequestDto);
        ApiResponse apiResponse = new ApiResponse(token);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Long userId) {
        UserDto user = authService.getUserById(userId);
        return ResponseEntity.ok(user);
    }


}
