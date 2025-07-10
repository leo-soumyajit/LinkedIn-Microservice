package com.soumyajit.LinkedInMicroservice.userService.DTOS;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class UserDto {
    private String name;
    private String email;
}
