package com.soumyajit.LinkedInMicroservice.connectionService.Consumer;

import com.soumyajit.LinkedInMicroservice.connectionService.Service.PersonService;
import com.soumyajit.LinkedInMicroservice.userService.Events.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceConsumer {

    private final PersonService personService;

    @KafkaListener(topics = "user_created_topic")
    public void handlePersonCreatedEvent(UserCreatedEvent userCreatedEvent){
        log.info("handlePersonCreatedEvent : {}",userCreatedEvent);
        personService.createPerson(userCreatedEvent.getUserId(),userCreatedEvent.getName());
    }

}
