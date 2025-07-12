package com.soumyajit.LinkedInMicroservice.connectionService.Controller;

import com.soumyajit.LinkedInMicroservice.connectionService.Entities.Person;
import com.soumyajit.LinkedInMicroservice.connectionService.Service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class ConnectionController {

    private final ConnectionService connectionService;

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<List<Person>> getFirstDegreeConnection(@PathVariable Long userId){
        return ResponseEntity.ok(connectionService.getFirstDegreeConnectionOfUser(userId));
    }
}
