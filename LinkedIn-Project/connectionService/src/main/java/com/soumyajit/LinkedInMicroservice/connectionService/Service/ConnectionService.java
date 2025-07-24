package com.soumyajit.LinkedInMicroservice.connectionService.Service;

import com.soumyajit.LinkedInMicroservice.connectionService.Auth.AuthContextHolder;
import com.soumyajit.LinkedInMicroservice.connectionService.Entities.Person;
import com.soumyajit.LinkedInMicroservice.connectionService.Events.ConnectionAcceptEvent;
import com.soumyajit.LinkedInMicroservice.connectionService.Exceptions.BadRequestException;
import com.soumyajit.LinkedInMicroservice.connectionService.Repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionService {
    private final PersonRepository personRepository;
    private final KafkaTemplate<Long, ConnectionAcceptEvent> acceptEventKafkaTemplate;


    public List<Person> getFirstDegreeConnectionOfUser(Long userId){
        log.info("Getting first degree connection of user with userid: {}",userId);

        return personRepository.getFirstDegreeConnections(userId);
    }

    public void sendConnectionRequest(Long receiverId){
        Long senderId = AuthContextHolder.getCurrentUserId();
        log.info("Sending the connection request with senderId : " +
                "{} to receiverId : {}",senderId,receiverId);

        if(senderId.equals(receiverId)){
            throw new com.soumyajit.LinkedInMicroservice.connectionService.Exceptions.BadRequestException("Both senderId and receiverId is same");
        }

        boolean alreadySendRequest = personRepository.connectionRequestExists(senderId,receiverId);
        if (alreadySendRequest){
            throw new com.soumyajit.LinkedInMicroservice.connectionService.Exceptions.BadRequestException("Connection request already exists cant send again");
        }

        boolean alreadyConnected = personRepository.alreadyConnected(senderId,receiverId);
        if(alreadyConnected){
            throw new com.soumyajit.LinkedInMicroservice.connectionService.Exceptions.BadRequestException("Already connected users, cant add connection request");
        }

        personRepository.addConnectionRequest(senderId,receiverId);
        log.info("Successfully send the connection request");


    }

    public void acceptConnectionRequest(Long senderId){
        Long receiverId = AuthContextHolder.getCurrentUserId();
        log.info("Accepting the connection request with senderId : " +
                "{} receiverId : {}",senderId,receiverId);

        if(senderId.equals(receiverId)){
            throw new com.soumyajit.LinkedInMicroservice.connectionService.Exceptions.BadRequestException("Both senderId and receiverId is same");
        }

        boolean alreadyConnected = personRepository.alreadyConnected(senderId,receiverId);
        if(alreadyConnected){
            throw new com.soumyajit.LinkedInMicroservice.connectionService.Exceptions.BadRequestException("Already connected users, cant accept connection request");
        }

        boolean alreadySendRequest = personRepository.connectionRequestExists(senderId,receiverId);
        if (!alreadySendRequest){
            throw new com.soumyajit.LinkedInMicroservice.connectionService.Exceptions.BadRequestException("Connection request isn't exists, cant accept connection request");
        }

        personRepository.acceptConnectionRequest(senderId,receiverId);
        log.info("Successfully accepted the connection request with senderId : " +
                "{} receiverId : {}",senderId,receiverId);

        ConnectionAcceptEvent connectionAcceptEvent = ConnectionAcceptEvent.builder()
                .receiverId(receiverId)
                .senderId(senderId)
                .build();
        // send notifications
        acceptEventKafkaTemplate.send("accept_connection_topic",connectionAcceptEvent);

    }

    public void rejectConnectionRequest(Long senderId){
        Long receiverId = AuthContextHolder.getCurrentUserId();
        log.info("Rejecting the connection request with senderId : " +
                "{} receiverId : {}",senderId,receiverId);

        if(senderId.equals(receiverId)){
            throw new BadRequestException("Both senderId and receiverId is same");
        }

        boolean alreadyConnected = personRepository.alreadyConnected(senderId,receiverId);
        if(alreadyConnected){
            throw new BadRequestException("Already connected users, cant reject connection request");
        }

        boolean alreadySendRequest = personRepository.connectionRequestExists(senderId,receiverId);
        if (!alreadySendRequest){
            throw new BadRequestException("Connection request isn't exists, cant reject connection request");
        }

        personRepository.rejectConnectionRequest(senderId,receiverId);
        log.info("Successfully rejected the connection request with senderId : " +
                "{} receiverId : {}",senderId,receiverId);

    }


}
