package com.soumyajit.LinkedInMicroservice.userService.DTOS;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
