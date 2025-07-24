package com.soumyajit.LinkedInMicroservice.connectionService.Events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConnectionAcceptEvent {
    private Long senderId;
    private Long receiverId;
}
