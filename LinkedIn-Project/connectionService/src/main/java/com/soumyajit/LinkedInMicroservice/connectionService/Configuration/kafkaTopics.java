package com.soumyajit.LinkedInMicroservice.connectionService.Configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.internals.Topic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class kafkaTopics {
    @Bean
    public NewTopic acceptConnectionTopic(){
        return new NewTopic("accept_connection_topic",3,(short) 1);
    }

    @Bean
    public NewTopic requestConnectionTopic(){
        return new NewTopic("request_connection_topic",3,(short) 1);
    }
}
