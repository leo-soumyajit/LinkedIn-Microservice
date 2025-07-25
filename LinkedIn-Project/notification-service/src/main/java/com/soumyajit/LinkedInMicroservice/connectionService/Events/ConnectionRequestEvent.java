package com.soumyajit.LinkedInMicroservice.connectionService.Events;

import lombok.Builder;
import lombok.Data;

@Data
public class ConnectionRequestEvent {
    private Long senderId;
    private Long receiverId;
}
