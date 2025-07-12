package com.soumyajit.LinkedInMicroservice.connectionService.Repository;

import com.soumyajit.LinkedInMicroservice.connectionService.Entities.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends Neo4jRepository<Person,Long> {

    Optional<Person> findByUserId(Long userId);

    @Query("match (personA:Person) -[:CONNECTED_TO]- (personB:Person) where personA.userId = $userId return personB")
    List<Person> getFirstDegreeConnections(Long userId);


}
