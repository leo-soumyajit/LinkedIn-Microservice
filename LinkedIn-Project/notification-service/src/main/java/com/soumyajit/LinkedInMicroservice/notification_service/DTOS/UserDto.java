package com.soumyajit.LinkedInMicroservice.notification_service.DTOS;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
}
