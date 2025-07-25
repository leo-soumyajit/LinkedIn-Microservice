package com.soumyajit.LinkedInMicroservice.connectionService.Events;

import lombok.*;

@Data
@Builder
public class ConnectionRequestEvent {
    private Long senderId;
    private Long receiverId;
}
