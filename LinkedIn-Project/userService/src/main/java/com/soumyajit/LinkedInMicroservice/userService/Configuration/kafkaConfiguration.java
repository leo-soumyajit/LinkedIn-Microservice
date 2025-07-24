package com.soumyajit.LinkedInMicroservice.userService.Configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class kafkaConfiguration {
    @Bean
    public NewTopic postCreatedTopic(){
        return new NewTopic("user_created_topic",3,(short) 1);
    }

}
