package com.soumyajit.LinkedInMicroservice.postService.DTOS;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class postDto {
    private Long id;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;
}
