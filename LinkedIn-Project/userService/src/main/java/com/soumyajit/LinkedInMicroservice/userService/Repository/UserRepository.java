package com.soumyajit.LinkedInMicroservice.userService.Repository;


import com.soumyajit.LinkedInMicroservice.userService.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
}
