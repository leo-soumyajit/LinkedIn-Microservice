package com.soumyajit.LinkedInMicroservice.connectionService.Service;

import com.soumyajit.LinkedInMicroservice.connectionService.Entities.Person;
import com.soumyajit.LinkedInMicroservice.connectionService.Repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionService {
    private final PersonRepository personRepository;


    public List<Person> getFirstDegreeConnectionOfUser(Long userId){
        log.info("Getting first degree connection of user with userid: {}",userId);

        return personRepository.getFirstDegreeConnections(userId);
    }


}
