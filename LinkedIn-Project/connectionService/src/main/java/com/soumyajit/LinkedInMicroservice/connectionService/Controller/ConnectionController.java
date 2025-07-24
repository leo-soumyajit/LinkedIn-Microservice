package com.soumyajit.LinkedInMicroservice.connectionService.Controller;

import com.soumyajit.LinkedInMicroservice.connectionService.Entities.Person;
import com.soumyajit.LinkedInMicroservice.connectionService.Service.ConnectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
@Slf4j
public class ConnectionController {

    private final ConnectionService connectionService;

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<List<Person>> getFirstDegreeConnection(@PathVariable Long userId){
        return ResponseEntity.ok(connectionService.getFirstDegreeConnectionOfUser(userId));
    }

    @PostMapping("/request/{userId}")
    public ResponseEntity<Void> sendConnectionRequest(@PathVariable Long userId){
        connectionService.sendConnectionRequest(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/accept/{userId}")
    public ResponseEntity<Void> acceptConnectionRequest(@PathVariable Long userId){
        connectionService.acceptConnectionRequest(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reject/{userId}")
    public ResponseEntity<Void> rejectConnectionRequest(@PathVariable Long userId){
        connectionService.rejectConnectionRequest(userId);
        return ResponseEntity.noContent().build();
    }
}
