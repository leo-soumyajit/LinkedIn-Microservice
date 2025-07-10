package com.soumyajit.LinkedInMicroservice.userService.DTOS;

import lombok.Data;

@Data
public class SignupRequestDto {
    private String name;
    private String email;
    private String password;
}
