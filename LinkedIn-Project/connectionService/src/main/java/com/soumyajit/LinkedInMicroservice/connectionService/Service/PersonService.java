package com.soumyajit.LinkedInMicroservice.connectionService.Service;

import com.soumyajit.LinkedInMicroservice.connectionService.Entities.Person;
import com.soumyajit.LinkedInMicroservice.connectionService.Repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {
    public final PersonRepository personRepository;
    public void createPerson(Long userId, String name){
        Person person = Person.builder()
                .userId(userId)
                .name(name)
                .build();
        personRepository.save(person);
    }
}
